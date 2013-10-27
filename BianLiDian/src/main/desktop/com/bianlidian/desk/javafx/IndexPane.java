package com.bianlidian.desk.javafx;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import org.apache.commons.lang3.time.DateFormatUtils;

import com.bianlidian.desk.service.OrderService;
import com.bianlidian.model.Order;
import com.bianlidian.model.OrderItem;

public class IndexPane extends GridPane {
	private Order order;
	private Text openId = new Text("openId：");
	private Text orderNumber = new Text("随机号：");
	private Text createDate = new Text("下单时间：");
	private TextArea orderDetail = new TextArea();
	// private Text totalPrice = new Text();
	private Text delivery = new Text("联系方式：");

	private TextField receiver = new TextField();
	private TextField address = new TextField();
	private TextField telephone = new TextField();

	private MainScene scene;

	// private TableView<OrderItem> orderItem = createItemTable();
	// private TextArea orderText = new TextArea();

	public IndexPane(MainScene scene) {
		// this.setMinWidth(800);
		// ColumnConstraints col1 = new ColumnConstraints();
		// col1.setPercentWidth(50);
		// ColumnConstraints col2 = new ColumnConstraints();
		// col2.setPercentWidth(50);
		// this.getColumnConstraints().addAll(col1, col2);

		this.setGridLinesVisible(true);
		this.add(new Text("订单详情"), 0, 0);

		receiver.setPromptText("收货人");
		address.setPromptText("收货地址");
		telephone.setPromptText("收货人电话");

		this.add(createOrder(), 0, 1);

		this.scene = scene;
		// orderText.setMinHeight(150);

		// final VBox vbox = new VBox();
		// vbox.getChildren().addAll(orderItem, orderText);

		// this.add(vbox, 1, 1);
	}

	public void init(Order order) {
		this.order = order;
		openId.setText("openId：" + order.getOpenId());
		orderNumber.setText("随机号：" + order.getOrderNumber());
		createDate.setText("下单时间："
				+ DateFormatUtils.format(order.getCreateDate(),
						"yyyy-MM-dd HH:mm:ss"));
		// totalPrice.setText("总金额：" + order.getTotalPrice());
		delivery.setText("联系方式：" + order.getDelivery());

		orderDetail.setText(text(order));

		receiver.setText(order.getReceiver());
		address.setText(order.getAddress());
		telephone.setText(order.getTelephone());

		// orderItem.setItems(FXCollections.observableList(order.getItems()));
		// orderItem.autosize();
		// orderText.setText(text(order));
	}

	private String text(Order order) {
		StringBuffer buf = new StringBuffer();
		for (OrderItem item : order.getItems()) {
			buf.append(item.getItemSku() + " " + item.getItemName() + " X"
					+ item.getItemNumber() + "\n");
		}
		buf.append("总价 " + order.getTotalPrice() + "元");
		return buf.toString();
	}

	/*
	 * private TableView<OrderItem> createItemTable() { TableView<OrderItem>
	 * table = new TableView<OrderItem>(); TableColumn<OrderItem, String>
	 * itemSku = new TableColumn<OrderItem, String>( "商品编码");
	 * itemSku.setMinWidth(100d); itemSku.setCellValueFactory(new
	 * PropertyValueFactory<OrderItem, String>( "itemSku"));
	 * 
	 * TableColumn<OrderItem, String> itemName = new TableColumn<OrderItem,
	 * String>( "商品名称"); itemName.setMinWidth(100d);
	 * itemName.setCellValueFactory(new PropertyValueFactory<OrderItem, String>(
	 * "itemName"));
	 * 
	 * TableColumn<OrderItem, Integer> itemNumber = new TableColumn<OrderItem,
	 * Integer>( "商品数量"); itemNumber.setMinWidth(100d); itemNumber
	 * .setCellValueFactory(new PropertyValueFactory<OrderItem, Integer>(
	 * "itemNumber"));
	 * 
	 * TableColumn<OrderItem, Double> itemPrice = new TableColumn<OrderItem,
	 * Double>( "商品金额"); itemPrice.setMinWidth(100d); itemPrice
	 * .setCellValueFactory(new PropertyValueFactory<OrderItem, Double>(
	 * "itemPrice"));
	 * 
	 * table.getColumns().addAll(itemSku, itemName, itemNumber, itemPrice);
	 * table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
	 * 
	 * return table; }
	 */

	private Node createOrder() {
		VBox box = new VBox();
		box.getChildren().add(openId);
		box.getChildren().add(orderNumber);
		box.getChildren().add(createDate);
		box.getChildren().add(new Separator());
		ScrollPane scroll = new ScrollPane();
		scroll.setContent(orderDetail);
		scroll.setPrefHeight(200);
		scroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
		orderDetail.setEditable(false);
		orderDetail.setMinHeight(190);
		orderDetail.setMinWidth(590);
		box.getChildren().add(scroll);
		// box.getChildren().add(totalPrice);
		box.getChildren().add(new Separator());
		box.getChildren().add(delivery);

		box.getChildren().add(receiver);
		box.getChildren().add(address);
		box.getChildren().add(telephone);

		Button undo = new Button("未处理");
		undo.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				undoOrder();
				scene.updateOrders();
				clear();
			}
		});

		Button handle = new Button("处理中");
		handle.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				handleOrder();
				scene.updateOrders();
				clear();
			}
		});

		Button deliver = new Button("配送中");
		deliver.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				deliverOrder();
				scene.updateOrders();
				clear();
			}
		});

		Button close = new Button("已完成");
		close.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				closeOrder();
				scene.updateOrders();
				clear();
			}
		});

		HBox hbox = new HBox();
		hbox.getChildren().addAll(undo, handle, deliver, close);

		box.getChildren().add(new Separator());
		box.getChildren().add(hbox);
		box.setMinHeight(380);
		box.setMinWidth(600);

		return box;
	}

	protected void clear() {
		order = null;
		openId.setText("openId：");
		orderNumber.setText("随机号：");
		createDate.setText("下单时间：");
		// totalPrice.setText("总金额：" + order.getTotalPrice());
		delivery.setText("联系方式：");

		orderDetail.setText("");

		receiver.setText("");
		address.setText("");
		telephone.setText("");
	}

	private void handleOrder() {
		order.setReceiver(receiver.getText());
		order.setAddress(address.getText());
		order.setTelephone(telephone.getText());

		OrderService.handleOrder(order);
	}

	private void undoOrder() {
		order.setReceiver(receiver.getText());
		order.setAddress(address.getText());
		order.setTelephone(telephone.getText());

		OrderService.undoOrder(order);
	}

	private void deliverOrder() {
		order.setReceiver(receiver.getText());
		order.setAddress(address.getText());
		order.setTelephone(telephone.getText());

		OrderService.deliverOrder(order);
	}

	private void closeOrder() {
		order.setReceiver(receiver.getText());
		order.setAddress(address.getText());
		order.setTelephone(telephone.getText());

		OrderService.closeOrder(order);
	}
}
