# s3-presigner

This project is used to generate pre-signed url for s3 objects using AWS IAM user. Using this url you can download the s3 Object. This url can be shared with any other users without exposing your credentials.

There are different ways you can generate the url.

* AWS Identity and Access Management (IAM) instance profile: Valid up to 6 hours
* AWS Security Token Service (STS): Valid up to 36 hours when signed with permanent credentials, such as the credentials of the AWS account root user or an IAM user
* IAM user: Valid up to 7 days when using AWS Signature Version 4. 

If you want maximum expiration time limit we need to use 3rd option.

#### Please refer resources/IAMUserPolicy.json for AWS IAM user policy.
