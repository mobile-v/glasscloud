package ru.vmsystems.template.interfaces.page;

import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.AbstractOrderedLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.UI;

@SpringUI(path = "/search")
@Theme("valo")
public class MainPageUi extends UI {

    @Override
    protected void init(VaadinRequest request) {
        AbstractOrderedLayout mainLayout = new HorizontalLayout();
        setContent(mainLayout);

        TabSheet tabsheet = new TabSheet();
        mainLayout.addComponent(tabsheet);
        mainLayout.setMargin(true);
        mainLayout.setSpacing(true);

//        tabsheet.addTab(searchBox, "Поиск");
    }
}
