package es.uc3m.buyer_seller.controller;


public class ProductController {

	public static final int MAX_STOCK=2147483647, MIN_STOCK=0, NAME_CHARACTER=100, SHORT_DESC_CHARACTER=300;// TODO

}


/* OPERATIONS
	 Product getProduct(int id){
	 List<Product> getProductsByCategory(String category){
	 List<Product> getProductsByCategory(int category){
	 List<Product> getProductsByCategories(List<Integer> idCategories) {
	 List<Product> getProductsBySeller(String email){
	 List<Product> getLastProducts(){
	 List<Product> getAllProducts(){
	public List<Product> getAllAvailableProducts() { Product.findAllAvailable
	 boolean deleteProduct(int id) {
	 boolean modifyProduct(Product p) {
	 int addProduct(Product p) {
	 void updateStock(Product product, int quantity) {
	 boolean verifyStock(int stock) {
	 List<Product> getProductByName(String title){
	 List<Product> getProductsBetweenPrices(int min, int max){
	 List<Product> getProductsBetweenSalePrices(int min, int max){
	 List<Product> getProductsByShipPrice(int price) {
	 List<Product> getProductsFreeShipment(){
	 List<Product> getProductsByStock(int min){
*/