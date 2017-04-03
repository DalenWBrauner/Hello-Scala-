import slick.driver.H2Driver.api._
import slick.lifted.{ProvenShape, ForeignKeyQuery}

// My dummy table for files
// id, userid, channelid, timestamp, filename, fileextension, filecontent
class Files(tag: Tag)
  extends Table[(Int, Int, Int, String, String, String, String)](tag, "FILES") {

  // god help us all
  def id: Rep[Int] = column[Int]("FILE_ID", O.PrimaryKey)// , O.AutoInc)
  def userid: Rep[Int] = column[Int]("USER_ID")
  def channelid: Rep[Int] = column[Int]("CHANNEL_ID")
  def timestamp: Rep[String] = column[String]("TIMESTAMP")
  def filename: Rep[String] = column[String]("FILENAME")
  def extension: Rep[String] = column[String]("EXTENSION")
  def content: Rep[String] = column[String]("CONTENT")


  def * : ProvenShape[(Int, Int, Int, String, String, String, String)] =
    (id, userid, channelid, timestamp, filename, extension, content)
}


// A Suppliers table with 6 columns: id, name, street, city, state, zip
class Suppliers(tag: Tag)
  extends Table[(Int, String, String, String, String, String)](tag, "SUPPLIERS") {

  // This is the primary key column:
  def id: Rep[Int] = column[Int]("SUP_ID", O.PrimaryKey)
  def name: Rep[String] = column[String]("SUP_NAME")
  def street: Rep[String] = column[String]("STREET")
  def city: Rep[String] = column[String]("CITY")
  def state: Rep[String] = column[String]("STATE")
  def zip: Rep[String] = column[String]("ZIP")

  // Every table needs a * projection with the same type as the table's type parameter
  def * : ProvenShape[(Int, String, String, String, String, String)] =
    (id, name, street, city, state, zip)
}

// A Coffees table with 5 columns: name, supplier id, price, sales, total
class Coffees(tag: Tag)
  extends Table[(String, Int, Double, Int, Int)](tag, "COFFEES") {

  def name: Rep[String] = column[String]("COF_NAME", O.PrimaryKey)
  def supID: Rep[Int] = column[Int]("SUP_ID")
  def price: Rep[Double] = column[Double]("PRICE")
  def sales: Rep[Int] = column[Int]("SALES")
  def total: Rep[Int] = column[Int]("TOTAL")

  def * : ProvenShape[(String, Int, Double, Int, Int)] =
    (name, supID, price, sales, total)

  // A reified foreign key relation that can be navigated to create a join
  def supplier: ForeignKeyQuery[Suppliers, (Int, String, String, String, String, String)] =
    foreignKey("SUP_FK", supID, TableQuery[Suppliers])(_.id)
}
