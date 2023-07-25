package tech.dinhphu28.springsec.Controllers;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.security.RolesAllowed;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tests")
public class TestController {

    @GetMapping(
        value = "/zz",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    // @PreAuthorize("hasAuthority('ADMIN')")
    // @RolesAllowed("ADMIN")
    public ResponseEntity<Object> retrieveAll() {
        ResponseEntity<Object> entity;

        List<String> strs = new ArrayList<String>();

        strs.add("Str 001");
        strs.add("Str 002");
        strs.add("Str 003");
        strs.add("Str 004");

        entity = new ResponseEntity<Object>(strs, HttpStatus.OK);

        return entity;
    }
}
