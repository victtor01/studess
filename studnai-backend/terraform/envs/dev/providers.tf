terraform {
	required_providers {
		aws = {
   		source  = "hashicorp/aws"
      version = "~> 5.0"
		}
	}
	
}

provider "aws" {
  region                      = var.aws_region
  skip_credentials_validation = true
  skip_metadata_api_check     = true
  s3_use_path_style           = true
  skip_requesting_account_id  = true

	// only dev
 	access_key = "test"
  secret_key = "test"


  endpoints {
    s3  = var.localstack_endpoint
    sqs = var.localstack_endpoint
    kms = var.localstack_endpoint
  }
}