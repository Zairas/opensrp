package org.ei.drishti.action;

import org.ei.drishti.scheduler.router.MilestoneEvent;
import org.ei.drishti.service.ActionService;
import org.ei.drishti.util.Event;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.motechproject.scheduletracking.api.domain.WindowName;

import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

public class CaptureANCReminderActionTest {
    @Mock
    private ActionService actionService;
    private DateTime dueWindowStart;
    private DateTime lateWindowStart;
    private DateTime maxWindowStart;
    private CaptureANCReminderAction reminderAction;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
        reminderAction = new CaptureANCReminderAction(actionService);

        dueWindowStart = DateTime.now();
        lateWindowStart = DateTime.now().plusDays(10);
        maxWindowStart = DateTime.now().plusDays(20);
    }

    @Test
    public void shouldRaiseNormalAlertActionsForDueWindowAlerts() throws Exception {
        reminderAction.invoke(event("Case 1", "Schedule 1", "Milestone 1", WindowName.due, dueWindowStart, lateWindowStart, maxWindowStart));

        verify(actionService).alertForBeneficiary("Case 1", "Milestone 1", "normal", dueWindowStart, lateWindowStart);
    }

    @Test
    public void shouldRaiseUrgentAlertActionsForLateWindowAlerts() throws Exception {
        reminderAction.invoke(event("Case 1", "Schedule 1", "Milestone 1", WindowName.late, dueWindowStart, lateWindowStart, maxWindowStart));

        verify(actionService).alertForBeneficiary("Case 1", "Milestone 1", "urgent", lateWindowStart, maxWindowStart);
    }

    private MilestoneEvent event(String externalID, String scheduleName, String milestone, WindowName window, DateTime dueWindowStart, DateTime lateWindowStart, DateTime maxWindowStart) {
        return new MilestoneEvent(Event.create().withSchedule(scheduleName).withMilestone(milestone).withWindow(window).withExternalId(externalID)
                .withDueWindowStartDate(dueWindowStart).withLateWindowStartDate(lateWindowStart).withMaxWindowStartDate(maxWindowStart).build());
    }
}
