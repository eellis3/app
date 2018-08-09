# Use Case Model

**Author**: Team35

## 1 Use Case Diagram

![Use Case Diagram](images/UseCaseDiagram.png)

## 2 Use Case Descriptions

The following section describes application use cases originating from the perspective of two primary actors, a Manager and a Customer.  While Managers are the only actors that interact directly with the application, it seemed prudent to include the Customer actor as well based on the integral role they play in the primary funtions of the application and their involvement in almost every feature of the system (i.e. purchases, payments, receiving notification emails, etc.).  

**Use Case Name:** Manage Customers  
**Requirements:** This use case describes the steps allowing a smoothie cart manager to manage a list of customers (and their individual attributes) maintained within the smoothie cart application.  
**Pre-Conditions:** None  
**Post-Conditions:** The customer list as well as the individual customer information will accurately reflect any changes made by the manager.  
**Scenarios:**  

  - 1.	Manage Customers  
    - 1.1.	Manager starts application  
    - 1.2.	Manager is taken to Home screen  
    - 1.3.	System displays list of customers to the Manager and prompts the Manager to select the desired activity pertaining to a customer: Add, Edit, History, Purchase  
    - 1.4.	If Manager selects Add, System allows Manager to add customer info such as Name, Email, and Billing Address…etc.  
      - 1.4.1.	System will provide customer information to a QR Code Card System that will produce a customer card which will be used to identify the customer during future transactions  
    - 1.5.	If Manager selects Edit, System allows Manager to edit customer info  
    - 1.6.	If Manager selects History, System allows Manager to view the customers purchase history  
    - 1.7.	If Manager selects Purchase, System allows Manager to process a customer purchase 

**Use Case Name:** Manage Purchase  
**Requirements:** This use case describes the steps allowing a smoothie cart manager to manage customer purchases of items sold at the smoothie cart using the smoothie cart application  
**Pre-Conditions:** The customer wishing to make a purchase must exist in the smoothie cart application.  The customer must provide their customer card to the manager for identification.  
**Post-Conditions:** The smoothie cart application will maintain an accurate list of items associated with the customers purchase and will provide detailed information about the purchase to the Manager.  
**Scenarios:**  

  - 2.	Manage Purchase  
    - 2.1.	Manager starts application or returns to the Home screen after completing another use case  
      - 2.1.1.	If Manager starts application they will be taken to Home screen automatically  
    - 2.2.	System displays list of customers to the Manager  
    - 2.3.	Manager choses a customer with whom to associate a Purchase  
    - 2.4.	Screen prompts Manager to add/remove a purchase item or view the Purchase details  
    - 2.5.	If Manager selects Add, System allows Manager to add a sale item (with details such as size and custom flavors) to the Purchase  
    - 2.6.	If Manager selects Remove, System allows Manager to remove an item from the Purchase  
    - 2.7.	Upon adding/removing an item from the Purchase, the System will display the items currently associated with the purchase to the Manager  
      - 2.7.1.	System allows Manager to view the details of the Purchase such as total, discounts applied, total before discounts, tax 
    - 2.8.	System will calculate and apply available rewards to the Purchase total  

**Use Case Name:**  Collect Payment  
**Requirements:** This use case describes the steps allowing a smoothie cart manager to collect a payment from a customer for items purchased at the smoothie cart through the smoothie cart application  
**Pre-Conditions:** Manager must have completed the Manage Purchase use case.  Customer must have a credit card that meets the smoothie cart payment systems criteria.  Customer information must include email for reward notification purposes  
**Post-Conditions:** System will record the purchase details associated with the purchase including date, items purchased, total amount, rewards earned, etc.  System will send reward notifications to customers when applicable.  
**Scenarios:**  

  - 3.	Collect Payment  
    - 3.1.	Manager must have completed Manage Purchase use case  
    - 3.2.	Manager takes credit card from customer  
    - 3.3.	Manager scans card using card scanner  
    - 3.4.	System receives transaction details from card scanner  
    - 3.5.	Manager can view the details of the card transaction for confirmation/verification  
    - 3.6.	System will calculate the customer rewards generated from transaction  
    - 3.7.	System will send rewards notification to customer when appropriate  

**Use Case Name:** Place Purchase  
**Requirements:** This use case describes the steps allowing a smoothie cart customer to purchase items sold at the smoothie cart  
**Pre-Conditions:** The customer must exist in the smoothie cart application and the customer must provide their customer card to the manager for identification.  
**Post-Conditions:** The smoothie cart application will maintain an accurate list of items associated with the customers purchase and will provide detailed information about the purchase to the Manager.  
**Scenarios:**  

  - 4.	Place Purchase  
    - 4.1.	A customer card is required to place an Purchase  
    - 4.2.	If a customer does not have a card then they must be added to the system via the Manage Customers use case  
    - 4.3.	Customer will provide Manager with their customer card  
    - 4.4.	Customer will verbally communicate to the Manager which items they would like to include in their Purchase  
    - 4.5.	System will allow Manager to modify the Purchase (add/remove) as appropriate and as stated in the Manage Purchase use case  

**Use Case Name:** Review Purchase  
**Requirements:** This use case describes the steps allowing a smoothie cart customer to review their purchase details  
**Pre-Conditions:** Customer must have completed the Place Purchase use case  
**Post-Conditions:** The smoothie cart application will maintain an accurate list of items associated with the customers purchase and will provide detailed information about the purchase to the Manager.  The manager is responsible for communicating the details back to the customer.  
**Scenarios:**  

  - 5.	Review Purchase  
    - 5.1.	If a customer desired to review the Purchase, they can request that the manager provide the Purchase details which is detailed in the Manage Purchase use case  

**Use Case Name:** Complete Sale  
**Requirements:** This use case describes the steps allowing a smoothie cart customer to review complete their purchase  
**Pre-Conditions:** Customer must have completed the Place Purchase use case.  Customer must have a credit card that meets the smoothie cart payment systems criteria.  
**Post-Conditions:** The customer’s credit card account should be debited the correct amount based on the sale purchase.  
**Scenarios:**  

  - 6.	Complete Sale  
    - 6.1.	Customer must have completed the Place Purchase use case  
    - 6.2.	Customer will provide a credit card to the Manager as a method of payment  
    - 6.3.	System will allow the manager to process the customer’s credit card via the Collect Payment use case  
    - 6.4.	If a customer desires to review the transaction, they can request that the manager provide the Purchase details which is detailed in the Manage Purchase use case or the Manage Customer, Purchase History use case  
    - 6.5.	System will calculate the customer rewards generated from transaction  
    - 6.6.	System will send rewards notification to customer when appropriate  
