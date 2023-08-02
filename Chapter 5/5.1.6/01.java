recommendation_result_feedback
  .join(recommendation_request, Seq("user_id", "video_id"), "left")
  .join(user, Seq("user_id"), "left")
  .join(video_item, Seq("video_id"), "left")
  .join(recommendation_model, Seq("user_id", "video_id"), "left")
  .join(user_behavior, Seq("user_id", "video_id"), "left")
  .repartition(filePartitions.toInt)
  .write
  .mode(SaveMode.Overwrite)
  .parquet("替换为实际的地址")