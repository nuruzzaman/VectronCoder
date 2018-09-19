// $Id: MainController.java, v1.0 Date:Sep 19, 2018 3:16:15 PM Engr.Nuruzzaman $

/*
 * Copyright(c) 2010-2018 eGudang (Malaysia). All Rights Reserved.
 * This software is the proprietary of "Artificial Casting".
 *
 */
package com.au.vectron.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 *
 * @author   : Mohammad Nuruzzaman
 * @email    : dr.zaman1981@gmail.com
 * @version  : v1.0, Date: Sep 19, 2018 3:16:15 PM
 */

@Controller
public class MainController {

	@GetMapping("/")
    public String index() {
        return "index";
    }
	
}
