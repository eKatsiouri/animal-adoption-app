Adoption Animal Platform

This project is a web-based platform for managing animal adoption, developed as the final project for Coding Factory 6.
It is built with Spring Boot for the backend and Angular for the frontend, showcasing a full-stack web application.
The application allows administrators to manage animals and adoption requests, and users to submit requests and adopt animals.


Administrator Features:
Create, update, and delete animals.
Manage adoption requests.


User Features:
Register and log in.
Submit adoption requests for animals available for adoption..
View their adoption requests status.


The backend provides the following key endpoints:

Animal Management From Admin:

POST /login/admin/animals - Create a new animal.

PUT /login/admin/animals/{id} - Update animal details.

DELETE /login/admin/animals/{id} - Delete an animal.

GET /login/admin/adoptions - Retrieve all pending adoption requests.

PATCH /login/admin/adoptions/{id} - Update the status of an adoption request.


Animal info:

GET /animals - Retrieve all animals.

GET /animals/adoption - Retrieve animals available for adoption.

GET /animals/{id} - Retrieve details of a specific animal.

Adoption Requests

POST /adoption-request - Submit an adoption request.



User Management

POST /auth/signup - Register a new user.

POST /auth/login - User login.

GET /login/user-details/{username} - Retrieve user profile.

PUT /login/user-details/{username} - Update user profile.


For detailed request and response examples, refer to the Swagger UI.



Angular Frontend Details

The frontend application provides the following main pages:

Home and About Page:
Displays a welcome message and brief introduction to the platform.
![Homepage](./images/Homepage.JPG)
![About Page](./images/AboutPage.JPG)


Login and Signup:
Allows users to register or log in to the platform.
![Login Page](./images/LoginPage.JPG)
![Register Page](./images/RegisterPage.JPG)


Animal List:
Shows all animals and animals available for adoption.
![All Animals](./images/AllAnimals.JPG)
![Animals For Adoption](./images/AnimalsForAdoption.JPG)
![More Info For A Specific Animal](./images/MoreInfoForASpecifinAnimal.JPG)

Adoption Requests:
Users can submit their adoption requests, and this page allows them to verify the details of the request before submitting.
![Verify The Adoption Request](./images/VerifyTheAdoptionRequest.JPG)
![Success Page](./images/SuccessPage.JPG)

User Profile:
The User Profile page displays the user’s personal information and the status of any adoption requests they have submitted.
![User Profile](./images/UserProfile.JPG)

On this page, users can update their profile information, such as their contact details, password, or preferences.
![Edit User Profile](./images/EditUserProfile.JPG)

Admin Dashboard:
Accessible to administrators for managing animals and adoption requests.

![Admin Profile](./images/AdminProfile.JPG)
The Admin Menu provides options to access different areas such as animal management and adoption requests.
![Admin Menu](./images/AdminMenu.JPG)

Administrators can manage the list of animals here, including adding, editing, or removing animals from the system.
![Manage Animals](./images/ManageAnimals.JPG)
Review all pending adoption requests.
![Manage Adoptions](./images/ManagaAdoptions.JPG)
Administrators can update the status of adoption requests ( Pending, Approved, Rejected).
![Change Adoption Request Status](./images/ChangeAdoptionRequestStatus.JPG)

Administrators can add new animals to the system, providing details such as the animal’s name, species, and availability for adoption.
![Add Animal](./images/AddAnimal.JPG)

Administrators can modify the details of an existing animal.
![Edit Animal Info](./images/EditAnimalInfo.JPG)



