package com.company.homeworkloans.screen.client;

import com.company.homeworkloans.screen.requestloan.Requestloan;
import io.jmix.ui.ScreenBuilders;
import io.jmix.ui.component.GroupTable;
import io.jmix.ui.screen.*;
import com.company.homeworkloans.entity.Client;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("Client.browse")
@UiDescriptor("client-browse.xml")
@LookupComponent("clientsTable")
public class ClientBrowse extends StandardLookup<Client> {
    @Autowired
    private ScreenBuilders screenBuilders;
    @Autowired
    private GroupTable<Client> clientsTable;

    @Subscribe("requestLoanAction")
    public void onRequestLoanAction(io.jmix.ui.action.Action.ActionPerformedEvent event) {
        Client selected = clientsTable.getSingleSelected();
        /*if (selected == null) {
            return;
        }*/

        screenBuilders.screen(this)
                .withScreenClass(Requestloan.class)
                .withOpenMode(OpenMode.DIALOG)
                .build()
                .withClient(selected)
                .show();

    }
}