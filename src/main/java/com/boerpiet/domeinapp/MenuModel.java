/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boerpiet.domeinapp;

/**
 *
 * @author Sonja
 */
public class MenuModel {
	int currentMenu = -1;

	public int getCurrentMenu() {
		return currentMenu;
	}

	public void setCurrentMenu(int currentMenu) {
		this.currentMenu = currentMenu;
	}
	
	public void changeCurrentMenu(int changeMenu) {
		if (currentMenu ==  1) { 		// Kazen
			pickFromCheeseMenu(changeMenu);

		} else if (currentMenu ==2 ){	// Account
			pickFromAccountMenu(changeMenu);		

		} else if (currentMenu == 3) {	// Klanten
			pickFromKlantenMenu(changeMenu);		
			
		} else if (currentMenu == 4) { 	// Bestelling
			pickFromBestellingMenu(changeMenu);		
			
		} else {						// Main menu
			if (changeMenu >= 1 && changeMenu <= 4) {
				currentMenu = changeMenu;
			}
		}		
	}
	
	private void pickFromCheeseMenu(int changeMenu) {
		if(changeMenu == 1) {
			
		} else if (changeMenu == 2) {
			
		} else if (changeMenu == 3) {
			currentMenu = 0;
		}
	}
	
	private void pickFromAccountMenu(int changeMenu) {
		if(changeMenu == 1) {
			
		} else if (changeMenu == 2) {
			
		} else if (changeMenu == 3) {
			currentMenu = 0;
		}
	}
	
	private void pickFromKlantenMenu(int changeMenu) {
		if(changeMenu == 1) {
			
		} else if (changeMenu == 2) {
			
		} else if (changeMenu == 3) {
			currentMenu = 0;
		}
	}
	
	private void pickFromBestellingMenu(int changeMenu) {
		if(changeMenu == 1) {
			
		} else if (changeMenu == 2) {
			
		} else if (changeMenu == 3) {
			currentMenu = 0;
		}
	}	
}
