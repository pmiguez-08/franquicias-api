variable "aws_region" {
  type        = string
  description = "Región de AWS"
  default     = "us-east-1"
}

variable "vpc_id" {
  type        = string
  description = "VPC donde se crea DocumentDB"
}

variable "subnet_ids" {
  type        = list(string)
  description = "Subnets privadas para DocumentDB"
}

variable "docdb_username" {
  type        = string
  description = "Usuario master de DocumentDB"
}

variable "docdb_password" {
  type        = string
  description = "Password master de DocumentDB"
  sensitive   = true
}
