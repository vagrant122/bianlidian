package com.bohua.bianlidian.desk.javafx;

import java.util.Date;
import java.util.List;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import com.bohua.bianlidian.desk.service.OrderService;
import com.bohua.bianlidian.desk.util.OrderAlerter;
import com.bohua.bianlidian.model.Order;

@SuppressWarnings("unchecked")
public class MainScene extends Application {
	private TableView<Order> table = new TableView<Order>();
	private IndexPane orderDetail = new IndexPane(this);
	private Pagination page = new Pagination(20, 0);
	private HBox menu = new HBox();
	private String orderStatus = OrderService.CREATED;
	private Button stopBeep = new Button("停止提示音");
	private OrderAlerter alerter;

	@Override
	public void start(Stage stage) throws Exception {
		Scene scene = new Scene(new Group());
		stage.setTitle("便利店后台");
		stage.setWidth(1000);
		stage.setHeight(800);

		final Label label = new Label("订单处理");
		label.setFont(new Font("Arial", 20));

		table.setEditable(true);
		table.getSelectionModel().selectedItemProperty()
				.addListener(new ChangeListener<Order>() {
					@Override
					public void changed(
							ObservableValue<? extends Order> observale,
							Order oldValue, Order newValue) {
						System.out.println(newValue);
						onRowSelected(newValue);
					}
				});

		TableColumn<Order, Integer> orderNumber = new TableColumn<Order, Integer>(
				"随机编号");
		orderNumber.setMinWidth(100d);
		orderNumber
				.setCellValueFactory(new PropertyValueFactory<Order, Integer>(
						"orderNumber"));

		TableColumn<Order, String> status = new TableColumn<Order, String>(
				"订单状态");
		status.setMinWidth(100d);
		status.setCellValueFactory(new PropertyValueFactory<Order, String>(
				"status"));

		TableColumn<Order, Date> createDate = new TableColumn<Order, Date>(
				"下单时间");
		createDate.setMinWidth(100d);
		createDate.setCellValueFactory(new PropertyValueFactory<Order, Date>(
				"createDate"));

		TableColumn<Order, Double> totalPrice = new TableColumn<Order, Double>(
				"总金额");
		totalPrice.setMinWidth(100d);
		totalPrice.setCellValueFactory(new PropertyValueFactory<Order, Double>(
				"totalPrice"));

		TableColumn<Order, String> receiver = new TableColumn<Order, String>(
				"收货人");
		receiver.setMinWidth(100d);
		receiver.setCellValueFactory(new PropertyValueFactory<Order, String>(
				"receiver"));

		TableColumn<Order, String> address = new TableColumn<Order, String>(
				"收货地址");
		address.setMinWidth(100d);
		address.setCellValueFactory(new PropertyValueFactory<Order, String>(
				"address"));

		TableColumn<Order, String> telephone = new TableColumn<Order, String>(
				"收货人电话");
		telephone.setMinWidth(100d);

		table.getColumns().addAll(orderNumber, status, createDate, totalPrice,
				receiver, address, telephone);

		table.setItems(getOrders());
		table.autosize();
		table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

		page.currentPageIndexProperty().addListener(
				new ChangeListener<Number>() {
					@Override
					public void changed(ObservableValue<? extends Number> arg0,
							Number arg1, Number arg2) {
						updateOrders();
					}
				});

		Button undo = new Button("未处理");
		undo.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				setOrderStatus(OrderService.CREATED);
				updateOrders();
			}
		});

		Button handle = new Button("处理中");
		handle.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				setOrderStatus(OrderService.HANDLED);
				updateOrders();
			}
		});

		Button deliver = new Button("配送中");
		deliver.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				setOrderStatus(OrderService.DILIVERED);
				updateOrders();
			}
		});

		Button close = new Button("已完成");
		close.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				setOrderStatus(OrderService.CLOSED);
				updateOrders();
			}
		});

		stopBeep.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				stopBeep();
			}
		});

		menu.getChildren().addAll(undo, handle, deliver, close);

		final VBox vbox = new VBox();
		vbox.setSpacing(5);
		vbox.setPadding(new Insets(10, 0, 0, 10));
		vbox.setMinWidth(850);
		vbox.setMaxHeight(750);
		vbox.getChildren().addAll(label, stopBeep, menu, table, page,
				orderDetail);

		((Group) scene.getRoot()).getChildren().addAll(vbox);

		// stage
		stage.setScene(scene);
		stage.show();

		new OrderAlerter(this).start();
	}

	private void stopBeep() {
		alerter.stopBeep();
	}

	public void updateOrders() {
		table.setItems(getOrders());
	}

	private ObservableList<Order> getOrders() {
		List<Order> orders = OrderService.getNewOrders(
				page.getCurrentPageIndex(), getOrderStatus());

		ObservableList<Order> list = FXCollections.observableList(orders);
		return list;
	}

	private void onRowSelected(Order newValue) {
		if (newValue != null) {
			orderDetail.init(newValue);
		}
	}

	public static void main(String[] args) {
		Application.launch(MainScene.class, args);
	}

	public OrderAlerter getAlerter() {
		return alerter;
	}

	public void setAlerter(OrderAlerter alerter) {
		this.alerter = alerter;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
}
