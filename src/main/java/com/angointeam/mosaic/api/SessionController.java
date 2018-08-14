package com.angointeam.mosaic.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
public class SessionController {

    @GetMapping("/session")
    public String getSession(HttpSession session, String uuid) {
        String memberUuid = (String) session.getAttribute("memberUuid");
        System.out.println(memberUuid);
        System.out.println(uuid);
        if (memberUuid == null) {
            memberUuid = uuid;
            session.setAttribute("memberUuid", memberUuid);
            return "create " + memberUuid + " session.";
        } else {
            memberUuid = (String)session.getAttribute("memberUuid");
        }
        return "load " + memberUuid + " session.";
    }

    @PostMapping("/newSession")
    public String newSession(HttpSession session,String uuid) {
        session.setAttribute("memberUuid", uuid);
        return "new " +session.getAttribute("memberUuid").toString() + " session.";
    }
}
