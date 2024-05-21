package com.oauth2.product;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * description : 产品
 *
 * @author kunlunrepo
 * date :  2024-05-20 15:33
 */
@RestController
public class ProductController {

    @RequestMapping("/findAll")
    public String findAll(){
        return "产品信息列表";
    }

}
