package org.ei.drishti.action;

import org.ei.drishti.scheduler.router.Action;
import org.ei.drishti.scheduler.router.MilestoneEvent;
import org.ei.drishti.service.ActionService;
import org.motechproject.scheduletracking.api.domain.WindowName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Qualifier("CaptureANCReminderAction")
public class CaptureANCReminderAction implements Action {
    ActionService actionService;

    @Autowired
    public CaptureANCReminderAction(ActionService actionService) {
        this.actionService = actionService;
    }

    @Override
    public void invoke(MilestoneEvent event, Map<String, String> extraData) {
        if (WindowName.late.toString().equals(event.windowName())) {
            actionService.alertForBeneficiary(event.externalId(), extraData.get("beneficiaryType"), event.milestoneName(), "urgent", event.startOfLateWindow(), event.startOfMaxWindow());
        } else {
            actionService.alertForBeneficiary(event.externalId(), extraData.get("beneficiaryType"), event.milestoneName(), "normal", event.startOfDueWindow(), event.startOfLateWindow());
        }
    }
}
