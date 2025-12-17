variable "aws_region" {
  type        = string
  description = "Región de AWS donde se aprovisiona la infraestructura"
  default     = "us-east-1"
}

variable "project_name" {
  type        = string
  description = "Nombre base para etiquetar recursos"
  default     = "franquicias"
}

variable "vpc_id" {
  type        = string
  description = "ID de la VPC donde se crea DocumentDB"
}

variable "vpc_cidr" {
  type        = string
  description = "CIDR de la VPC para permitir tráfico interno hacia DocumentDB"
}

variable "subnet_ids" {
  type        = list(string)
  description = "Lista de subnets privadas donde se despliega DocumentDB"
}

variable "docdb_username" {
  type        = string
  description = "Usuario administrador del cluster DocumentDB"
}

variable "docdb_password" {
  type        = string
  description = "Contraseña del usuario administrador del cluster DocumentDB"
  sensitive   = true
}

variable "docdb_instance_class" {
  type        = string
  description = "Tipo de instancia para DocumentDB"
  default     = "db.t3.medium"
}
