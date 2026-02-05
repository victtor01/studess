resource "aws_s3_bucket" "jobs" {
  bucket = "studnai-dev-jobs-bucket"
}

resource "aws_s3_object" "paths" {
  for_each = toset([
    "jobs/raw/",
    "jobs/processed/",
    "jobs/output/",
    "users/avatars/",
    "users/documents/"
  ])

  bucket  = aws_s3_bucket.jobs.id
  key     = each.value
  content = ""
}

resource "aws_sqs_queue" "ingest_dlq" {
  name = "studnai-dev-ingest-dlq"
}

resource "aws_sqs_queue" "ingest" {
  name                      = "studnai-dev-ingest-queue"
  visibility_timeout_seconds = 60

  redrive_policy = jsonencode({
    deadLetterTargetArn = aws_sqs_queue.ingest_dlq.arn
    maxReceiveCount     = 5
  })
}

resource "aws_sqs_queue" "generate_dlq" {
  name = "studnai-dev-generate-dlq"
}

resource "aws_sqs_queue" "generate" {
  name                      = "studnai-dev-generate-queue"
  visibility_timeout_seconds = 60

  redrive_policy = jsonencode({
    deadLetterTargetArn = aws_sqs_queue.generate_dlq.arn
    maxReceiveCount     = 5
  })
}

