package controllers;

import play.mvc.Controller;
import play.mvc.With;

@With(Seguranca.class)
public class Home extends Controller {

    public static void home() {
         render();
    }
}
