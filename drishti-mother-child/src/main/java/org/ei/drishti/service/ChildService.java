package org.ei.drishti.service;

import org.ei.drishti.contract.ChildCloseRequest;
import org.ei.drishti.domain.Child;
import org.ei.drishti.domain.Mother;
import org.ei.drishti.form.domain.FormSubmission;
import org.ei.drishti.repository.AllChildren;
import org.ei.drishti.repository.AllMothers;
import org.ei.drishti.service.formSubmission.handler.ReportFieldsDefinition;
import org.ei.drishti.service.reporting.ChildReportingService;
import org.ei.drishti.service.scheduling.ChildSchedulesService;
import org.ei.drishti.util.SafeMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.ei.drishti.common.AllConstants.ANCFormFields.REFERENCE_DATE;
import static org.ei.drishti.common.AllConstants.ChildBirthCommCareFields.BF_POSTBIRTH_FIELD_NAME;
import static org.ei.drishti.common.AllConstants.ChildImmunizationFields.PREVIOUS_IMMUNIZATIONS_FIELD_NAME;
import static org.ei.drishti.common.AllConstants.ChildRegistrationECFields.CHILD_ID;
import static org.ei.drishti.common.AllConstants.DeliveryOutcomeFields.DID_BREAST_FEEDING_START;
import static org.ei.drishti.common.AllConstants.Form.ID;
import static org.ei.drishti.common.AllConstants.Report.REPORT_EXTRA_DATA_KEY_NAME;

@Service
public class ChildService {
    public static final String IMMUNIZATIONS_SEPARATOR = " ";
    private static Logger logger = LoggerFactory.getLogger(ChildService.class.toString());
    private ChildSchedulesService childSchedulesService;
    private AllMothers allMothers;
    private AllChildren allChildren;
    private ChildReportingService childReportingService;
    private ActionService actionService;
    private ReportFieldsDefinition reportFieldsDefinition;

    @Autowired
    public ChildService(ChildSchedulesService childSchedulesService,
                        AllMothers allMothers,
                        AllChildren allChildren,
                        ChildReportingService childReportingService, ActionService actionService, ReportFieldsDefinition reportFieldsDefinition) {
        this.childSchedulesService = childSchedulesService;
        this.allMothers = allMothers;
        this.allChildren = allChildren;
        this.childReportingService = childReportingService;
        this.actionService = actionService;
        this.reportFieldsDefinition = reportFieldsDefinition;
    }

    public void registerChildren(FormSubmission submission) {
        Mother mother = allMothers.findByCaseId(submission.entityId());
        if (mother == null) {
            logger.warn("Failed to handle children registration as there is no mother registered with id: " + submission.entityId());
            return;
        }

        List<Child> children = allChildren.findByMotherId(submission.entityId());

        for (Child child : children) {
            child = child.withAnm(submission.anmId()).withDateOfBirth(submission.getField(REFERENCE_DATE)).withThayiCard(mother.thayiCardNo());
            allChildren.update(child);

            SafeMap reportingData = new SafeMap();
            reportingData.put(ID, child.caseId());
            reportingData.put(BF_POSTBIRTH_FIELD_NAME, submission.getField(DID_BREAST_FEEDING_START));
            childReportingService.registerChild(reportingData);

            childSchedulesService.enrollChild(child);
        }
    }

    public void registerChildrenForEC(FormSubmission submission) {
        Child child = allChildren.findByCaseId(submission.getField(CHILD_ID));
        child.withAnm(submission.anmId());
        allChildren.update(child);
    }

    public void updateChildImmunization(FormSubmission submission) {
        if (!allChildren.childExists(submission.entityId())) {
            logger.warn("Found immunization update without registered child for entity ID: " + submission.entityId());
            return;
        }

        String previousImmunizationsField = isBlank(submission.getField(PREVIOUS_IMMUNIZATIONS_FIELD_NAME))
                ? "" : submission.getField(PREVIOUS_IMMUNIZATIONS_FIELD_NAME);
        List<String> previousImmunizations = Arrays.asList(previousImmunizationsField.split(IMMUNIZATIONS_SEPARATOR));

        SafeMap reportFieldsMap = new SafeMap(submission.getFields(reportFieldsDefinition.get("child_immunizations")));
        childReportingService.immunizationProvided(reportFieldsMap, previousImmunizations);

        childSchedulesService.updateEnrollments(submission.entityId(), previousImmunizations);
    }

    @Deprecated
    public void closeChildCase(ChildCloseRequest childCloseRequest, Map<String, Map<String, String>> extraData) {
        if (!allChildren.childExists(childCloseRequest.caseId())) {
            logger.warn("Found close child request without registered child for case ID: " + childCloseRequest.caseId());
            return;
        }

        allChildren.close(childCloseRequest.caseId());
        actionService.closeChild(childCloseRequest.caseId(), childCloseRequest.anmIdentifier());
        childReportingService.closeChild(new SafeMap(extraData.get(REPORT_EXTRA_DATA_KEY_NAME)));
        childSchedulesService.unenrollChild(childCloseRequest.caseId());
    }
}
