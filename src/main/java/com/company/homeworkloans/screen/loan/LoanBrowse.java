package com.company.homeworkloans.screen.loan;

import io.jmix.ui.component.Table;
import io.jmix.ui.model.CollectionLoader;
import io.jmix.ui.screen.*;
import com.company.homeworkloans.entity.Loan;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("Loan.browse")
@UiDescriptor("loan-browse.xml")
@LookupComponent("loansTable")
public class LoanBrowse extends StandardLookup<Loan> {

    @Autowired
    private CollectionLoader<Loan> loansDl;


   /* @Subscribe
    public void onInit(InitEvent event) {
        loansDl.setParameter("statusId", "R");
    }*/

    @Subscribe
    public void onBeforeShow(BeforeShowEvent event) {
        loansDl.setParameter("statusId", "R");
        loansDl.load();
    }

    /*@Subscribe
    public void onInit(InitEvent event) {
        loansTable.addGeneratedColumn("Age", entity -> {

        });
    }*/





}