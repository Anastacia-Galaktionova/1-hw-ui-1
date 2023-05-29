package com.company.homeworkloans.screen.requestloan;

import com.company.homeworkloans.entity.Client;
import com.company.homeworkloans.entity.Loan;
import com.company.homeworkloans.entity.LoanStatus;
import io.jmix.core.DataManager;
import io.jmix.core.Metadata;
import io.jmix.core.metamodel.datatype.Datatype;
import io.jmix.core.metamodel.datatype.impl.BigDecimalDatatype;
import io.jmix.core.metamodel.model.MetaClass;
import io.jmix.ui.component.Button;
import io.jmix.ui.component.EntityComboBox;
import io.jmix.ui.component.TextField;
import io.jmix.ui.component.ValidationErrors;
import io.jmix.ui.screen.*;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.text.ParseException;
import java.time.LocalDate;

@UiController("Requestloan")
@UiDescriptor("RequestLoan.xml")
public class Requestloan extends Screen {

    @Autowired
    private ScreenValidation screenValidation;
    @Autowired
    private EntityComboBox<Client> ClientCb;
    @Autowired
    private DataManager dataManager;
    @Autowired
    private TextField AmountTf;
    @Autowired
    private Metadata metadata;

    @Subscribe("commitAndCloseBtn")
    public void onCommitAndCloseBtnClick(Button.ClickEvent event) {
        ValidationErrors errors = screenValidation.validateUiComponents(ClientCb.getFrame());
        if (!errors.isEmpty()) {
            screenValidation.showValidationErrors(this, errors);
        }
        else {
            Loan loan = dataManager.create(Loan.class);
            loan.setClient(ClientCb.getValue());
            loan.setAmount(parseAmountValue(AmountTf.getValue().toString()));
            loan.setRequestDate(LocalDate.now());
            loan.setStatus(LoanStatus.REQUESTED);
            dataManager.save(loan);
            this.closeWithDefaultAction();
        }

    }

    @Subscribe("CancelBtn")
    public void onCancelBtnClick(Button.ClickEvent event) {
        close(StandardOutcome.CLOSE);
    }


    public BigDecimal parseAmountValue(String stringValue) {
        MetaClass metaClass = metadata.getClass(Loan.class);
        Datatype<BigDecimal> amountDatatype = metaClass.getProperty("amount")
                .getRange().asDatatype();
        assert amountDatatype instanceof BigDecimalDatatype;
        try {
            return amountDatatype.parse(stringValue);
        } catch (ParseException e) {
            throw new RuntimeException("Cannot parse amount", e);
        }
    }


    public Requestloan withClient(@Nullable Client client) {
        if (client != null) {
            ClientCb.setValue(client);
        }
        return this;
    }

}