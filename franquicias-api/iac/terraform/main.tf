resource "aws_security_group" "docdb_sg" {
  name        = "franquicias-docdb-sg"
  description = "Security group para DocumentDB"
  vpc_id      = var.vpc_id

  ingress {
    description = "Permite MongoDB desde la misma VPC"
    from_port   = 27017
    to_port     = 27017
    protocol    = "tcp"
    cidr_blocks = ["10.0.0.0/8"]
  }

  egress {
    description = "Salida a internet para actualizaciones administradas"
    from_port   = 0
    to_port     = 0
    protocol    = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }
}

resource "aws_docdb_subnet_group" "docdb_subnets" {
  name       = "franquicias-docdb-subnet-group"
  subnet_ids = var.subnet_ids
}

resource "aws_docdb_cluster" "docdb_cluster" {
  cluster_identifier     = "franquicias-docdb-cluster"
  engine                 = "docdb"
  master_username        = var.docdb_username
  master_password        = var.docdb_password
  db_subnet_group_name   = aws_docdb_subnet_group.docdb_subnets.name
  vpc_security_group_ids = [aws_security_group.docdb_sg.id]

  skip_final_snapshot = true
}

resource "aws_docdb_cluster_instance" "docdb_instance" {
  identifier         = "franquicias-docdb-instance-1"
  cluster_identifier = aws_docdb_cluster.docdb_cluster.id
  instance_class     = "db.t3.medium"
}
