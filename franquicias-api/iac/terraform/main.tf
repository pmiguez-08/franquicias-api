# Security Group para permitir conexión al puerto de Mongo (27017)
resource "aws_security_group" "docdb_sg" {
  name        = "${var.project_name}-docdb-sg"
  description = "Permite acceso a DocumentDB por puerto 27017 desde la red interna"
  vpc_id      = var.vpc_id

  ingress {
    description = "Acceso interno a DocumentDB desde el CIDR de la VPC"
    from_port   = 27017
    to_port     = 27017
    protocol    = "tcp"
    cidr_blocks = [var.vpc_cidr]
  }

  egress {
    description = "Permite salida general"
    from_port   = 0
    to_port     = 0
    protocol    = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }

  tags = {
    Name      = "${var.project_name}-docdb-sg"
    Project   = var.project_name
    ManagedBy = "terraform"
    Component = "database"
  }
}

# Grupo de subnets donde AWS coloca DocumentDB
resource "aws_docdb_subnet_group" "docdb_subnets" {
  name       = "${var.project_name}-docdb-subnet-group"
  subnet_ids = var.subnet_ids

  tags = {
    Name      = "${var.project_name}-docdb-subnet-group"
    Project   = var.project_name
    ManagedBy = "terraform"
  }
}

# Cluster DocumentDB
resource "aws_docdb_cluster" "docdb_cluster" {
  cluster_identifier     = "${var.project_name}-docdb-cluster"
  engine                 = "docdb"
  master_username        = var.docdb_username
  master_password        = var.docdb_password
  db_subnet_group_name   = aws_docdb_subnet_group.docdb_subnets.name
  vpc_security_group_ids = [aws_security_group.docdb_sg.id]

  # Para una prueba técnica se evita snapshot final para que destroy sea simple
  skip_final_snapshot = true

  tags = {
    Name      = "${var.project_name}-docdb-cluster"
    Project   = var.project_name
    ManagedBy = "terraform"
    Component = "database"
  }
}

# Instancia del cluster DocumentDB
resource "aws_docdb_cluster_instance" "docdb_instance" {
  identifier         = "${var.project_name}-docdb-instance-1"
  cluster_identifier = aws_docdb_cluster.docdb_cluster.id
  instance_class     = var.docdb_instance_class

  tags = {
    Name      = "${var.project_name}-docdb-instance-1"
    Project   = var.project_name
    ManagedBy = "terraform"
    Component = "database"
  }
}

