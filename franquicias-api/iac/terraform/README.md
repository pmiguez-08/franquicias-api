# IaC Terraform - AWS DocumentDB

Esta carpeta aprovisiona la persistencia en AWS usando Amazon DocumentDB (compatible con MongoDB).

Requisitos:
- Tener cuenta de AWS
- Tener AWS CLI configurado con credenciales
- Tener Terraform instalado

Pasos:
1) terraform init
2) terraform fmt
3) terraform validate
4) terraform plan -var-file="terraform.tfvars"
5) terraform apply -var-file="terraform.tfvars"

Para destruir:
terraform destroy -var-file="terraform.tfvars"
