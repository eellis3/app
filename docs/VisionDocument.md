# Vision Document

**Author**: Team35

## 1 Introduction

SmoothieCart is an Android based application that will allow for the processing of credit card payments as well the management rewards for smoothie cart customers.  It is intended to be used by a smoothie cart employee to automatically apply credits and discounts to purchases for the most loyal customers.

## 2 Business Needs/Requirements

Brad and Janet are two Atlanta area entrepreneurs who sell organic smoothies in Piedmont Park.  Their weekend business has been successful and is growing rapidly.  To better serve their growing customer base, Brad and Janet would like to offer their customers the ability to purchase smoothies using a credit card.  Additionally, Brad and Janet would like to provide their most loyal customers with multiple types of rewards.

At present, there is no system on the Android platform that combines both the ability to process credit card payments and provide a rewards management system.  Brad and Janet would like to offer a $5.00 credit for single purchases of $50.00 or more and a lifetime 5% discount for individuals who spend $500.00 within a calendar year.  The system must be able to automatically track purchases and apply any available credits and discounts.

The smoothie cart manager must be able to add customers to the system, edit information associated with existing customers, and view individual purchase histories.  The system must to be able to uniquely identify customers in order to associate them with their purchase histories and available discounts.  Customers should also be notified via email when certain credits or discounts become available to them.

## 3 Product / Solution Overview

SmoothieCart will be an Android application that is a database of smoothie cart customers and their purchase histories.  Via a graphical user interface, it will provide a manager the ability to add customers to the database, to edit existing customer information, and to view purchase histories for each customer.

Upon addition to the database, new customers will be provided with a loyalty card with a unique QR code.  This code will be associated with the account information of that customer.  Also at the time of account creation, each customer will be asked to provide the manager with the following information:
- Name
- Billing Address
- Email Address

At the time of purchase the customer's loyalty card will be scanned.  The QR code will be used to identify the customer and retrieve any applicable credits and discounts that the customer may have earned.  These will be automatically applied to the customer's balance.

An external credit card scanner and its associated card scanning software will be used to process payements.  Likewise, an external email management software package will be incorparated into the SmoothieCart system to handle email notifications.

## 4 Major Features

- Home Screen:  This is the screen that will be opened upon launching the SmoothieCart application.  From this screen the user will have the ability to access the customer database or process a transaction.
- Add Customer Screen:  From this screen the user will be able to enter the customer's account information and associate the loyalty card QR code with the customer.
- Edit Customer Screen:  This screen provides the user the ability to edit the information for customers who already exist within the system.
- Customer Info Screen:  This screen displays the current state of the customer information as saved in the system.  From this screen, the user can view the customer's purchase history, make a new purchase, or edit the customer's information.
- Purchase History Screen:  This screen shows the user information regarding a customer's previous purchases.
- Select Item Screen:  This screen allows the user to select a type of smoothie and set the price for that smoothie.
- Smoothie Details Screen:  This screen provide additional smoothie flavor and size options along with pricing information.
- Purchase:  Interfaces with the credit card scanner and payment processor software.
- Scan Loyalty Card:  This will activate the camera on the Android device and will register the loyalty card QR code.

## 5 Scope and Limitations

The initial version of SmoothieCart will focus on providing customers with the option to pay via credit card.  It will also apply available rewards to a customer's purchase and notify them via email when a reward has been earned.  

However, the SmoothieCart system assumes that only a single employee is running the system and that the application can only be run on a single device.  Some design considerations have been made that would allow for the system to be more easily extended as the business grows (i.e. allowing for multiple employees).  Also, customer's will be required to pay via a credit card in order to be eligible for any of the rewards.  Lastly, a limited set of menu items will be available in the initial release of software.
