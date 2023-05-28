package com.company.homeworkloans.screen.loan;

import com.company.homeworkloans.entity.LoanStatus;
import io.jmix.core.DataManager;
import io.jmix.ui.Notifications;
import io.jmix.ui.action.Action;
import io.jmix.ui.component.GroupTable;
import io.jmix.ui.model.CollectionLoader;
import io.jmix.ui.screen.*;
import com.company.homeworkloans.entity.Loan;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

@UiController("Loan.browse")
@UiDescriptor("loan-browse.xml")
@LookupComponent("loansTable")
public class LoanBrowse extends StandardLookup<Loan> {

    @Autowired
    private CollectionLoader<Loan> loansDl;
    @Autowired
    private GroupTable<Loan> loansTable;
    @Autowired
    private DataManager dataManager;
    @Autowired
    private Notifications notifications;


   /* @Subscribe
    public void onInit(InitEvent event) {
        loansDl.setParameter("statusId", "R");
    }*/

    @Subscribe
    public void onBeforeShow(BeforeShowEvent event) {
        loansDl.setParameter("statusId", "R");
        loansDl.load();
    }

    @Subscribe("approveAction")
    public void onApproveAction(Action.ActionPerformedEvent event) {

        Set<Loan> loanToApprove = loansTable.getSelected();
        Loan loan = loanToApprove.stream().findFirst().get();
        loan.setStatus(LoanStatus.APPROVED);
        dataManager.save(loan);
        loansDl.load();
        notifications.create().withCaption("Approved").show();

    }

    @Subscribe("rejectAction")
    public void onRejectAction(Action.ActionPerformedEvent event) {
        Set<Loan> loanToApprove = loansTable.getSelected();
        Loan loan = loanToApprove.stream().findFirst().get();
        loan.setStatus(LoanStatus.REJECTED);
        dataManager.save(loan);
        loansDl.load();
        notifications.create().withCaption("Rejected").show();
    }




    /*@Subscribe
    public void onInit(InitEvent event) {
        loansTable.addGeneratedColumn("Age", entity -> {

        });
    }*/





}