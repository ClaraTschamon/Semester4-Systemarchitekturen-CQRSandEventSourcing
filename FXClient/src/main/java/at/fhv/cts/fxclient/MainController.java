package at.fhv.cts.fxclient;

import at.fhv.cts.fxclient.domainModel.RemoteBooking;
import at.fhv.cts.fxclient.domainModel.RemoteCustomer;
import at.fhv.cts.fxclient.domainModel.RemoteEvent;
import at.fhv.cts.fxclient.domainModel.RemoteRoom;
import at.fhv.cts.fxclient.rest.IRestFacade;
import at.fhv.cts.fxclient.rest.ReadsideAdapterImpl;
import at.fhv.cts.fxclient.rest.RestFacadeImpl;
import at.fhv.cts.fxclient.rest.WritesideAdapterImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ResourceBundle;
import java.util.UUID;
import java.util.stream.Collectors;

public class MainController implements Initializable {

    private IRestFacade restFacade;

    private final String FONT_BOLD = "-fx-font-weight: bold";
    private final Label cancelBookingResultLabel = new Label();
    @FXML
    private ScrollPane scrollpane;
    private VBox root;
    private GridPane firstGridPane;
    private GridPane getCustomersGridPane;
    private GridPane getBookingsGridPane;
    private GridPane cancelBookingGridPane;
    private GridPane bookRoomsGridPane;
    private GridPane searchFreeRoomsGridPane;
    private GridPane customerInfoGridPane;
    private GridPane allEventsGridPane;

    private TableView<RemoteCustomer> customersTableView;
    private TableColumn<RemoteCustomer, UUID> customerIdColumn;
    private TableColumn<RemoteCustomer, String> customerNameColumn;
    private TableColumn<RemoteCustomer, String> customerAddressColumn;
    private TableColumn<RemoteCustomer, LocalDate> customerDateOfBirthColumn;

    private TableView<RemoteBooking> bookingsTableView;
    private TableColumn<RemoteBooking, UUID> bookingIdColumn;
    private TableColumn<RemoteBooking, LocalDate> bookingArrivalDateColumn;
    private TableColumn<RemoteBooking, LocalDate> bookingDepartureDateColumn;
    private TableColumn<RemoteBooking, RemoteCustomer> bookingCustomerColumn;
    private TableColumn<RemoteBooking, Integer> bookingRoomsColumn;

    private TableView<RemoteRoom> freeRoomsTableView;
    private TableColumn<RemoteRoom, Integer> roomNumberColumn;
    private TableColumn<RemoteRoom, Integer> maxPersonsColumn;
    private TableColumn<RemoteRoom, String> roomCategoryColumn;

    private TableView<RemoteEvent> eventsTableView;
    private TableColumn<RemoteEvent, LocalDateTime> eventTimestampColumn;
    private TableColumn<RemoteEvent, String> eventTypeColumn;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ReadsideAdapterImpl readsideAdapter = new ReadsideAdapterImpl();
        WritesideAdapterImpl writesideAdapter = new WritesideAdapterImpl();
        restFacade = new RestFacadeImpl(readsideAdapter, writesideAdapter);

        root = new VBox();
        scrollpane.setContent(root);
        scrollpane.setFitToWidth(true);
        scrollpane.setFitToHeight(true);


        scrollpane.setFitToHeight(true);
        scrollpane.setFitToWidth(true);

        firstGridPane = new GridPane();
        firstGridPane.getStyleClass().add("gridpane");

        root.getChildren().add(firstGridPane);

        getCustomersGridPane = new GridPane();
        getCustomersGridPane.getStyleClass().add("gridpane");
        root.getChildren().add(getCustomersGridPane);

        getBookingsGridPane = new GridPane();
        getBookingsGridPane.getStyleClass().add("gridpane");
        root.getChildren().add(getBookingsGridPane);

        cancelBookingGridPane = new GridPane();
        cancelBookingGridPane.getStyleClass().add("gridpane");
        root.getChildren().add(cancelBookingGridPane);

        bookRoomsGridPane = new GridPane();
        bookRoomsGridPane.getStyleClass().add("gridpane");
        root.getChildren().add(bookRoomsGridPane);

        searchFreeRoomsGridPane = new GridPane();
        searchFreeRoomsGridPane.getStyleClass().add("gridpane");
        root.getChildren().add(searchFreeRoomsGridPane);

        customerInfoGridPane = new GridPane();
        customerInfoGridPane.getStyleClass().add("gridpane");
        root.getChildren().add(customerInfoGridPane);

        allEventsGridPane = new GridPane();
        allEventsGridPane.getStyleClass().add("gridpane");
        root.getChildren().add(allEventsGridPane);

        addElemetsToGridpane();
    }

    private void addElemetsToGridpane() {
        Label initializeDBsLabel = new Label("Press 'Initialize DBs' to initialize the databases");
        initializeDBsLabel.setStyle(FONT_BOLD);
        firstGridPane.add(initializeDBsLabel, 0, 0);

        Button initializeDBsButton = new Button();
        initializeDBsButton.setText("Initialize DBs");
        initializeDBsButton.setOnAction(event -> restFacade.initializeDBs());
        firstGridPane.add(initializeDBsButton, 0, 1);

        Label restoreDBsLabel = new Label("Press 'Restore DBs' to restore the databases");
        restoreDBsLabel.setStyle(FONT_BOLD);
        firstGridPane.add(restoreDBsLabel, 0, 2);

        Button restoreDBsButton = new Button();
        restoreDBsButton.setText("Restore DBs");
        restoreDBsButton.setOnAction(event -> restFacade.restoreDBs());
        firstGridPane.add(restoreDBsButton, 0, 3);

        Label deleteDBsLabel = new Label("Press 'Delete DBs' to delete the databases");
        deleteDBsLabel.setStyle(FONT_BOLD);
        firstGridPane.add(deleteDBsLabel, 0, 4);

        Button deleteDBsButton = new Button();
        deleteDBsButton.setText("Delete DBs");
        deleteDBsButton.setOnAction(event -> restFacade.deleteDBs());
        firstGridPane.add(deleteDBsButton, 0, 5);

        addGetCustomersFunctionality();

        addGetBookingsFunctionality();

        addCancelBookingFunctionality();

        addBookRoomsFunctionality();

        addGetAllEventsFunctionality();
    }

    private void addGetCustomersFunctionality() {
        getCustomersGridPane.add(new Separator(), 0, 0, 3, 1);

        Label customerNameLabel = new Label("Search for customers");
        customerNameLabel.setStyle(FONT_BOLD);
        getCustomersGridPane.add(customerNameLabel, 0, 1);
        TextField customerNameTextField = new TextField();
        customerNameTextField.setPromptText("Name (optional)");
        getCustomersGridPane.add(customerNameTextField, 0, 2);

        Button searchCustomersButton = new Button();
        searchCustomersButton.setText("Search");
        getCustomersGridPane.add(searchCustomersButton, 1, 2);
        searchCustomersButton.setOnAction(event -> {
            List<RemoteCustomer> customersList = restFacade.getCustomers(customerNameTextField.getText());

            if (customersList != null) {
                ObservableList<RemoteCustomer> customersObservableList = FXCollections.observableArrayList(customersList);

                customerIdColumn = new TableColumn<>("id");
                customerIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
                customerNameColumn = new TableColumn<>("Name");
                customerNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
                customerAddressColumn = new TableColumn<>("Address");
                customerAddressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
                customerDateOfBirthColumn = new TableColumn<>("Date of Birth");
                customerDateOfBirthColumn.setCellValueFactory(new PropertyValueFactory<>("dateOfBirth"));

                customersTableView = new TableView<>(customersObservableList);
                customersTableView.setMinHeight(200);
                customersTableView.getColumns().addAll(customerIdColumn, customerNameColumn, customerAddressColumn, customerDateOfBirthColumn);
                getCustomersGridPane.add(customersTableView, 0, 3);
                GridPane.setColumnSpan(customersTableView, 4);
                customersTableView.setMinWidth(800);
                customersTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
            }
        });

    }

    private void addGetBookingsFunctionality() {
        getBookingsGridPane.add(new Separator(), 0, 0, 3, 1);
        Label bookingsLabel = new Label("Search for bookings");
        bookingsLabel.setStyle(FONT_BOLD);
        getBookingsGridPane.add(bookingsLabel, 0, 1);
        Label arrivalDateLabel = new Label("select arrival date");
        getBookingsGridPane.add(arrivalDateLabel, 0, 2);
        DatePicker arrivalDatePicker = new DatePicker();
        getBookingsGridPane.add(arrivalDatePicker, 0, 3);

        Label departureDate = new Label("select departure date");
        getBookingsGridPane.add(departureDate, 1, 2);
        DatePicker departureDatePicker = new DatePicker();
        getBookingsGridPane.add(departureDatePicker, 1, 3);


        Button searchBookingsButton = new Button();
        searchBookingsButton.setText("Search");
        getBookingsGridPane.add(searchBookingsButton, 2, 3);

        //disables button if one of the datepickers is empty
        searchBookingsButton.disableProperty().bind(arrivalDatePicker.valueProperty().isNull().or(departureDatePicker.valueProperty().isNull()));

        searchBookingsButton.setOnAction(event -> {
            List<RemoteBooking> bookingList = restFacade.getBookings(arrivalDatePicker.getValue(), departureDatePicker.getValue());

            if (bookingList != null) {
                ObservableList<RemoteBooking> bookingsObservableList = FXCollections.observableArrayList(bookingList);

                bookingIdColumn = new TableColumn<>("Booking Number");
                bookingIdColumn.setCellValueFactory(new PropertyValueFactory<>("bookingId"));
                bookingArrivalDateColumn = new TableColumn<>("Arrival Date");
                bookingArrivalDateColumn.setCellValueFactory(new PropertyValueFactory<>("fromDate"));
                bookingDepartureDateColumn = new TableColumn<>("Departure Date");
                bookingDepartureDateColumn.setCellValueFactory(new PropertyValueFactory<>("toDate"));
                bookingCustomerColumn = new TableColumn<>("Customer");
                bookingCustomerColumn.setCellValueFactory(new PropertyValueFactory<>("customer"));
                bookingRoomsColumn = new TableColumn<>("Rooms");
                bookingRoomsColumn.setCellValueFactory(new PropertyValueFactory<>("rooms"));

                bookingsTableView = new TableView<>(bookingsObservableList);
                bookingsTableView.setMinHeight(200);
                bookingsTableView.getColumns().addAll(bookingIdColumn, bookingArrivalDateColumn, bookingDepartureDateColumn,
                        bookingCustomerColumn, bookingRoomsColumn);
                bookingCustomerColumn.setMinWidth(200);
                bookingsTableView.setMinWidth(800);
                bookingsTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
                getBookingsGridPane.add(bookingsTableView, 0, 4);
                GridPane.setColumnSpan(bookingsTableView, 5);
            }
        });
    }


    private void addCancelBookingFunctionality() {
        cancelBookingGridPane.add(new Separator(), 0, 0, 3, 1);

        Label cancelBookingLabel = new Label("Cancel Booking");
        cancelBookingLabel.setStyle(FONT_BOLD);
        cancelBookingGridPane.add(cancelBookingLabel, 0, 1);

        TextField bookingIdTextField = new TextField();
        bookingIdTextField.setPromptText("Booking Number");
        cancelBookingGridPane.add(bookingIdTextField, 0, 2);

        Button cancelBookingButton = new Button();
        cancelBookingButton.setText("Cancel Booking");
        cancelBookingGridPane.add(cancelBookingButton, 1, 2);

        cancelBookingGridPane.add(cancelBookingResultLabel, 0, 3);

        cancelBookingButton.disableProperty().bind(bookingIdTextField.textProperty().isEmpty());

        cancelBookingButton.setOnAction(event -> {
            boolean success = restFacade.cancelBooking(bookingIdTextField.getText());
            if (success) {
                cancelBookingResultLabel.setText("Booking successfully cancelled");
                cancelBookingResultLabel.setStyle("-fx-text-fill: green");
            } else {
                cancelBookingResultLabel.setText("Booking could not be cancelled");
                cancelBookingResultLabel.setStyle("-fx-text-fill: red");
            }
        });
    }

    private void addBookRoomsFunctionality() { //includes getFreeRoomsFunctionality and createCustomerFunctionality
        bookRoomsGridPane.add(new Separator(), 0, 0, 3, 1);
        Label bookRoomsLabel = new Label("Book Rooms");
        bookRoomsLabel.setStyle(FONT_BOLD);
        bookRoomsGridPane.add(bookRoomsLabel, 0, 1);

        Label arrivalDate = new Label("select Arrival Date");
        bookRoomsGridPane.add(arrivalDate, 0, 2);

        DatePicker arrivalDateDatePicker = new DatePicker();
        bookRoomsGridPane.add(arrivalDateDatePicker, 0, 3);

        Label departureDate = new Label("select Departure Date");
        bookRoomsGridPane.add(departureDate, 1, 2);
        DatePicker departureDateDatePicker = new DatePicker();
        bookRoomsGridPane.add(departureDateDatePicker, 1, 3);

        Label maxPersons = new Label("select number of persons");
        bookRoomsGridPane.add(maxPersons, 0, 4);

        Spinner<Integer> spinner = new Spinner<>(0, 20, 2);
        bookRoomsGridPane.add(spinner, 0, 5);

        Label searchForRooms = new Label("Press 'Search for Rooms' to search for free rooms");
        bookRoomsGridPane.add(searchForRooms, 1, 4);

        Button searchForRoomsButton = new Button();
        searchForRoomsButton.setText("Search for Rooms");
        searchForRoomsButton.disableProperty().bind(arrivalDateDatePicker.valueProperty().isNull()
                .or(departureDateDatePicker.valueProperty().isNull())
                .or(spinner.valueProperty().isNull()));
        bookRoomsGridPane.add(searchForRoomsButton, 1, 5);
        searchForRoomsButton.setOnAction(event -> onSearchForRoomsButtonClicked(
                arrivalDateDatePicker.getValue(),
                departureDateDatePicker.getValue(),
                spinner.getValue()
        ));
    }

    private void onSearchForRoomsButtonClicked(LocalDate arrivalDate, LocalDate departureDate, int maxPersons) {
        //remove content from customerInfoGridPane
        customerInfoGridPane.getChildren().removeAll(customerInfoGridPane.getChildren());

        Label label = new Label("Select a Room");
        customerInfoGridPane.add(label, 0, 0);

        freeRoomsTableView = new TableView<>();
        freeRoomsTableView.setMinHeight(200);
        freeRoomsTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        List<RemoteRoom> remoteRoomList = restFacade.getFreeRooms(arrivalDate, departureDate, maxPersons);

        if (remoteRoomList != null) {
            ObservableList<RemoteRoom> remoteRoomObservableList = FXCollections.observableArrayList(remoteRoomList);

            roomNumberColumn = new TableColumn<>("Room Number");
            roomNumberColumn.setCellValueFactory(new PropertyValueFactory<>("roomNo"));
            maxPersonsColumn = new TableColumn<>("Persons");
            maxPersonsColumn.setCellValueFactory(new PropertyValueFactory<>("maxPersons"));
            roomCategoryColumn = new TableColumn<>("Room Type");
            roomCategoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
            freeRoomsTableView.getColumns().addAll(roomNumberColumn, maxPersonsColumn, roomCategoryColumn);
            freeRoomsTableView.setItems(remoteRoomObservableList);

            //order tableview by category
            freeRoomsTableView.getSortOrder().add(roomCategoryColumn);
            //order tableview by room number
            roomNumberColumn.setSortType(TableColumn.SortType.ASCENDING);

            freeRoomsTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
            customerInfoGridPane.add(freeRoomsTableView, 0, 1);
            GridPane.setColumnSpan(freeRoomsTableView, 3);
        }
        Label customerData = new Label("Enter your customer Data");
        customerData.setStyle(FONT_BOLD);
        customerInfoGridPane.add(customerData, 0, 6);
        TextField nameTextField = new TextField();
        nameTextField.setPromptText("name");
        customerInfoGridPane.add(nameTextField, 0, 8);

        TextField addressTextField = new TextField();
        addressTextField.setPromptText("address");
        customerInfoGridPane.add(addressTextField, 1, 8);

        Label dateOfBirthLabel = new Label("select Date of Birth");
        customerInfoGridPane.add(dateOfBirthLabel, 2, 7);
        DatePicker dateOfBirthDatePicker = new DatePicker();
        customerInfoGridPane.add(dateOfBirthDatePicker, 2, 8);

        Button bookRoomButton = new Button();
        bookRoomButton.setText("Book Room(s)");
        bookRoomButton.disableProperty().bind(nameTextField.textProperty().isEmpty()
                .or(addressTextField.textProperty().isEmpty())
                .or(dateOfBirthDatePicker.valueProperty().isNull()));
        Label bookingSuccessLabel = new Label();
        customerInfoGridPane.add(bookingSuccessLabel, 0, 11);

        bookRoomButton.setOnAction(event -> {
            String customerId = restFacade.createCustomer(
                    nameTextField.getText(),
                    addressTextField.getText(),
                    dateOfBirthDatePicker.getValue());
            List<Integer> selectedRooms = freeRoomsTableView.getSelectionModel().getSelectedItems().stream()
                    .map(RemoteRoom::getRoomNo)
                    .collect(Collectors.toList());
            boolean success = restFacade.bookRooms(
                    arrivalDate,
                    departureDate,
                    selectedRooms,
                    customerId);

            if (success) {
                bookingSuccessLabel.setText("Booking successfully created");
                bookingSuccessLabel.setStyle("-fx-text-fill: green");
            } else {
                bookingSuccessLabel.setText("Booking could not be created");
                bookingSuccessLabel.setStyle("-fx-text-fill: red");
            }
        });
        customerInfoGridPane.add(bookRoomButton, 0, 10);
    }

    private void addGetAllEventsFunctionality() {
        allEventsGridPane.add(new Separator(), 0, 0, 3, 1);
        Label getAllEventsLabel = new Label("Press 'Get All Events' to get all events");
        getAllEventsLabel.setStyle(FONT_BOLD);
        allEventsGridPane.add(getAllEventsLabel, 0, 1);

        Button getAllEventsButton = new Button();
        getAllEventsButton.setText("Get All Events");
        allEventsGridPane.add(getAllEventsButton, 0, 2);

        getAllEventsButton.setOnAction(event -> {
            List<RemoteEvent> remoteEventList = restFacade.getAllEvents();
            if (remoteEventList != null) {
                ObservableList<RemoteEvent> remoteEventObservableList = FXCollections.observableArrayList(remoteEventList);
                eventsTableView = new TableView<>();
                eventsTableView.setMinHeight(300);
                eventsTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

                eventTimestampColumn = new TableColumn<>("Timestamp");
                eventTimestampColumn.setCellValueFactory(new PropertyValueFactory<>("timestamp"));
                eventTypeColumn = new TableColumn<>("Event Type");
                eventTypeColumn.setCellValueFactory(new PropertyValueFactory<>("className"));
                eventsTableView.getColumns().addAll(eventTimestampColumn, eventTypeColumn);
                eventsTableView.setItems(remoteEventObservableList);
                eventsTableView.setMinWidth(800);
                eventsTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY); //HIER LÖSUNG
                allEventsGridPane.add(eventsTableView, 0, 3);
            }
        });
    }
}
