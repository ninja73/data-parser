package utils

import org.slf4j.LoggerFactory

trait Logging {
  self: Object ⇒
  val logger = LoggerFactory.getLogger(getClass)
  val logName = self.getClass.getSimpleName

  @inline
  def log_info(msg: ⇒ String): Unit = logger.info(s"$logName $msg")

  @inline
  def log_debug(msg: ⇒ String): Unit = logger.debug(s"$logName $msg")

  @inline
  def log_warn(msg: ⇒ String): Unit = logger.warn(s"$logName $msg")

  @inline
  def log_error(msg: ⇒ String): Unit = logger.error(s"$logName $msg")
}
