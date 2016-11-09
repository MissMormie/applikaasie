/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boerpiet.controllerapp;

import com.boerpiet.*;
import com.boerpiet.domeinapp.MenuModel;
import java.util.Scanner;
import com.boerpiet.viewapp.MenuView;
/**
 *
 * @author Sonja
 */
public class MenuController {
	MenuModel menuPojo;
	MenuView menuView;
	Scanner input = new Scanner(System.in);
			

	public static void main(String[] args) {
		new MenuController(new MenuModel(), new MenuView());
	}
	
	public MenuController(MenuModel menuPojo, MenuView menuView) {
		this.menuPojo = menuPojo;
		this.menuView = menuView;
		showMenu();
	}
	
	public void showMenu() {
		menuView.showCurrentMenu(menuPojo.getCurrentMenu());
		menuPojo.changeCurrentMenu(listenForMenuInput());
		showMenu();
	}
	
	
	// todo catch exception for wrong input
	public int listenForMenuInput() {
		int menuChoice;
		try {
			menuChoice = input.nextInt();
			System.out.print(" choice doen");
		} catch (Exception ex) {
			menuChoice = menuPojo.getCurrentMenu();
		}
		return menuChoice;
	}
	

}