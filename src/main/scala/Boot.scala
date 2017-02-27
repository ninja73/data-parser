import config.SparkCore.sc
import transform.ClientTransform
import utils.Logging

object Boot extends App with  Logging {
  args.headOption match {
    case Some(path) ⇒
      log_info("Start App")
      log_info(s"Load file: $path")
      val clients = sc.textFile(path)
      val clientsCSV = ClientTransform(clients).normalize

      val outFile = path + "_normalize"
      log_info(s"Save file: $outFile")
      clientsCSV.saveAsTextFile(outFile)
      sc.stop()
    case None ⇒
      log_error("Argument filePath not found")
  }
}
