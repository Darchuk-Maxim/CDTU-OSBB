package com.cdtu;

import org.rapidoid.annotation.Controller;
import org.rapidoid.annotation.Page;
import org.rapidoid.gui.Btn;
import org.rapidoid.gui.GUI;
import org.rapidoid.gui.input.Form;
import org.rapidoid.html.tag.FormTag;
import org.rapidoid.http.Req;
import org.rapidoid.security.annotation.Administrator;
import org.rapidoid.security.annotation.Manager;
import org.rapidoid.setup.Admin;
import org.rapidoid.setup.My;
import org.rapidoid.u.U;

import java.awt.print.Book;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Максим on 16.06.2017.
 */
@Controller("/bar")
public class Tag {

//    @Manager
//    @Page("/hi")
//    public Object i(Req req) {
//        System.out.println(req.token().get("_user"));
//        movie.title = "Chappie";
//

//        Btn save = GUI.btn("Save").primary().go("/save");
//        Form form = GUI.edit(movie).buttons(save);
//        List<Object> p = new ArrayList<>();
//        return GUI.col12(GUI.col4(form),GUI.col4(form),GUI.col4(form),GUI.col4(form),GUI.col4(form));
//    }

}
