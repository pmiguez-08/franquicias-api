output "docdb_endpoint" {
  description = "Endpoint de DocumentDB"
  value       = aws_docdb_cluster.docdb_cluster.endpoint
}
