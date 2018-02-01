package ru.vmsystems.template.interfaces.page;

class URLS {

    static final String PAGE_SELECT_RECEPTION = "/glass/login-reception-of-order";

    static final String PAGE_REDIRECT_TO_LOGIN = "redirect:/login";
    static final String PAGE_REDIRECT_TO_LOGOUT = "redirect:/logout";
    static final String PAGE_REDIRECT_TO_ADMIN = "redirect:/admin/admin";
    static final String PAGE_REDIRECT_TO_SELECT_RECEPTION = "redirect:" + PAGE_SELECT_RECEPTION;

    static final String PAGE_REDIRECT_TO_ORDERS = "redirect:/glass/order";

    static final String PAGE_REDIRECT_TO_MATERIAL_COLORS = "redirect:/glass/material/color";
    static final String PAGE_REDIRECT_TO_MATERIAL_TYPES = "redirect:/glass/material/type";
    static final String PAGE_REDIRECT_TO_PROCESS_TYPES = "redirect:/glass/process/type";
    static final String PAGE_REDIRECT_TO_RECEPTIONS = "redirect:/glass/reception";
}
