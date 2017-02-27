package transform

import org.apache.spark.rdd.RDD

class ClientTransform(clients: RDD[String])
  extends BaseTransform[ClientInfo]
  with Serializable {

  object parser extends ClientParser with SimpleParsers

  def normalize: RDD[String] =
    clients
      .flatMap(parser.parseRoot)
      .map (clientToCSVRow)


  def clientToCSVRow(client: ClientInfo): String =
    s"${client.userId}, ${client.experience}, ${client.ordersCount}, ${client.paymentSum}, ${client.region}"

}

object ClientTransform {

  def apply(clients: RDD[String]): ClientTransform =
    new ClientTransform(clients)
}
