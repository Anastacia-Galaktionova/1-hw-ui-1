package com.company.homeworkloans.screen.loan;

import com.company.homeworkloans.entity.LoanStatus;
import io.jmix.core.DataManager;
import io.jmix.ui.Notifications;
import io.jmix.ui.UiComponents;
import io.jmix.ui.action.Action;
import io.jmix.ui.component.Component;
import io.jmix.ui.component.GroupTable;
import io.jmix.ui.component.Label;
import io.jmix.ui.model.CollectionLoader;
import io.jmix.ui.screen.*;
import com.company.homeworkloans.entity.Loan;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

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
    @Autowired
    private UiComponents uiComponents;


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

    @Install(to = "loansTable.clientAge", subject = "columnGenerator")
    private Component loansTableClientAgeColumnGenerator(Loan loan) {
        Label ageLabel = uiComponents.create(Label.class);

        LocalDate date = loan.getClient().getBirthDate();

        long age = ChronoUnit.YEARS.between(date, LocalDate.now());

        ageLabel.setValue(age);
        return ageLabel;
    }





}