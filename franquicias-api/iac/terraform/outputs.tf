output "docdb_endpoint" {
  description = "Endpoint del cluster DocumentDB"
  value       = aws_docdb_cluster.docdb_cluster.endpoint
}

output "docdb_port" {
  description = "Puerto de DocumentDB"
  value       = aws_docdb_cluster.docdb_cluster.port
}

output "docdb_username" {
  description = "Usuario configurado para DocumentDB"
  value       = var.docdb_username
}
