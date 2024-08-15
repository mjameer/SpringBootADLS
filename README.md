This code setup will aid in ADLS file upload and file download. 

### changes required
- Update application.properties with your ADLS information.

### File upload 
- Use http://localhost:8080/api/file/upload to upload. Api will return the Files SAS URL as a response. 

![image](https://github.com/user-attachments/assets/12bc8ce6-19fe-47f4-a554-9c8aa7dd93bd)

### Download
- Use http://localhost:8080/api/file/download/textpost.txt to download.

![image](https://github.com/user-attachments/assets/0ab3de6f-6e03-4554-8822-d37d7321ff73)


