package seedu.clinic.logic.commands;

import seedu.clinic.model.Model;

/**
 * Format full help instructions for respective help command to display.
 */
public class HelpCommand extends Command {

    public static final String COMMAND_WORD = "help";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows all command with"
            + " respective utility description\n"
            + "Example: " + COMMAND_WORD + "\n\nSpecific commands with respective input samples "
            + "can be shown as well \n" + "Example: help find";
    public static final String MESSAGE_TOO_MANY_ARGUMENTS = "You can only enter one command at a time\n\n"
            + MESSAGE_USAGE;
    public static final String MESSAGE_WRONG_ARGUMENT = "You can only enter command from one of the "
            + "following keywords: add, clear, delete, edit, exit, find, view, update";

    public static final String[] ALLOWED_ARGUMENTS = new String[]{
        "add", "clear", "delete", "edit", "exit", "find", "list", "view", "update"};

    public static final String HELP_MESSAGE_FOR_COMMAND_FORMAT = "How to interpret command format?\n"
            + "Words in UPPER_CASE are the parameters to be supplied by the user."
            + " Items in square brackets are optional. Items with ... after them can be used multiple times."
            + " Parameters can be in any order.";

    public static final String HELP_MESSAGE_FOR_USER_GUIDE = "Press F1 to copy the URL to view User Guide"
            + " on our website for more details on each command sample";

    private final String commandArgument;

    /**
     * Constructs a new HelpCommand object.
     *
     * @param commandArgument takes in the command argument (if any) specified for help command.
     */
    public HelpCommand(String commandArgument) {
        this.commandArgument = commandArgument;
    }

    @Override
    public CommandResult execute(Model model) {
        String helpMessage = "test";
        switch(commandArgument) {
        case "add":
            helpMessage = generateHelpAddMessage();
            break;
        case "clear":
            helpMessage = generateHelpClearMessage();
            break;
        case "delete":
            helpMessage = generateHelpDeleteMessage();
            break;
        case "edit":
            helpMessage = generateHelpEditMessage();
            break;
        case "exit":
            helpMessage = generateHelpExitMessage();
            break;
        case "find":
            helpMessage = generateHelpFindMessage();
            break;
        case "list":
            helpMessage = generateHelpListMessage();
            break;
        case "view":
            helpMessage = generateHelpViewMessage();
            break;
        case "update":
            helpMessage = generateHelpUpdateMessage();
            break;
        default:
            helpMessage = generateHelpGenericMessage();
        }

        return new CommandResult(helpMessage, false, false);
    }

    private String generateHelpGenericMessage() {
        String aboutHelpCommand = "You can view more of each command using \nhelp COMMAND.\n\n"
                + "Sample Command:\nhelp find\n\nShows what find command does, command format and"
                + " sample command(s) to follow.\n\n"
                + "If you wish to learn more about sample commands found under"
                + " each command, feel free to copy our website User Guide URL,"
                + " which can be found by pressing F1.";

        String aboutAddCommand = "add\nYou can add warehouse, supplier using the add command";
        String aboutClearCommand = "clear\nYou can clear all entries (suppliers and warehouses)"
                + " in CLI-nic using clear command.";
        String aboutDeleteCommand = "delete\nYou can delete warehouse, supplier or products associated to"
                + " them that are no longer needed by using the delete command.";
        String aboutEditCommand = "edit\nYou can edit warehouse or supplier by using the edit command. Name"
                + ", Phone, Remarks can be edited in both warehouse and suppliers. In addition, a"
                + " warehouse's address and a supplier's email can be edited as well.";
        String aboutExitCommand = "exit\nYou can exit the application using the exit command.";
        String aboutFindCommand = "find\nYou can find suppliers or warehouses using the find command.";
        String aboutListCommand = "list\nYou can list all suppliers and warehouses by using list command.";
        String aboutViewCommand = "view\nYou can view a specific warehouse or supplier using"
                + " the view command.";
        String aboutUpdateCommand = "update\nYou can update a product in a supplier or warehouse by"
                + " using the update command";

        String finalGenericHelpMessage = aboutHelpCommand + "\n\n" + aboutAddCommand + "\n\n"
                + aboutClearCommand + "\n\n" + aboutDeleteCommand + "\n\n" + aboutEditCommand + "\n\n"
                + aboutExitCommand + "\n\n" + aboutFindCommand + "\n\n" + aboutListCommand + "\n\n"
                + aboutViewCommand + "\n\n" + aboutUpdateCommand;
        return finalGenericHelpMessage;
    }

    private String generateHelpAddMessage() {
        String generalDescriptionOfAdd = "Add\nAdd command allows you to add warehouse or supplier"
                + " to a supplier.";

        String addWarehouse = "Add Warehouse \nAdds warehouse to the CLI-nic application.";
        String addWarehouseCommandFormat = "Command format: \nadd ct/TYPE n/WAREHOUSE_NAME p/PHONE"
                + " addr/ADDRESS [r/WAREHOUSE_REMARK]\nThe TYPE specified here should be w for warehouse.";
        String addWarehouseSampleCommand = "Sample Command: \nadd ct/w n/warehouseA p/00000000"
                + " addr/John street, block 123, #01-01 r/First warehouse";
        String finalAddWarehouse = addWarehouse + "\n\n" + addWarehouseCommandFormat + "\n\n"
                + addWarehouseSampleCommand;

        String addSupplier = "Add Supplier \nAdds a supplier to the CLI-nic application.";
        String addSupplierCommandFormat = "Command format: \nadd ct/TYPE n/SUPPLIER_NAME p/PHONE"
                + " e/EMAIL_ADDRESS [r/SUPPLIER_REMARK]\nThe TYPE specified here should be s for supplier.";
        String addSupplierSampleCommand = "Sample Command: \nadd ct/s n/Philips Pharmaceutical p/00000000"
                + " e/philipsPharm@gmail.com r/largest contractor";
        String finalAddSupplier = addSupplier + "\n\n" + addSupplierCommandFormat + "\n\n"
                + addSupplierSampleCommand;

        String addHelpMessage = HELP_MESSAGE_FOR_COMMAND_FORMAT + "\n\n" + generalDescriptionOfAdd + "\n\n"
                + finalAddWarehouse + "\n\n" + finalAddSupplier + "\n\n" + HELP_MESSAGE_FOR_USER_GUIDE;

        return addHelpMessage;
    }

    private String generateHelpClearMessage() {
        return "Clear\n\nClear all entries (suppliers and warehouses) in CLI-nic.\n\nSample Command: \nclear";
    }

    private String generateHelpDeleteMessage() {
        String deleteSupplierOrWarehouse = "Delete\nYou can delete warehouse/supplier"
                + " or products associated with them.\n\nDeletes a particular warehouse or supplier\n"
                + "The TYPE specified should be one of these values: w / s.\n"
                + "The INDEX must be a positive integer, not exceeding the total number of items.";
        String deleteSupplierOrWarehouseCommandFormat = "Command format: \ndelete ct/TYPE i/INDEX";
        String deleteSupplierOrWarehouseSampleCommand = "Sample Command: \ndelete delete ct/w i/1 \n"
                + "delete ct/s i/12";

        String deleteProductInstruction = "Deletes a product entry no longer stored by a certain warehouse"
                + " or sold by a specific supplier.\nThe TYPE specified should be one of these"
                + " values: pw / ps.\nThe PRODUCT_NAME must be identifiable and starts with"
                + " alphanumeric character.\n"
                + "The product with the PRODUCT_NAME should be in the INDEX-th supplier/warehouse"
                + " in the displayed list.";
        String deleteProductCommandFormat = "Command format: \ndelete ct/TYPE i/INDEX pd/PRODUCT_NAME";
        String deleteProductSampleCommand = "Sample Command: \ndelete ct/pw i/1 pd/Panadol\n"
                + "delete ct/ps i/12 pd/Aspirin";

        String deleteHelpMessage = HELP_MESSAGE_FOR_COMMAND_FORMAT + "\n\n" + deleteSupplierOrWarehouse
                + "\n\n" + deleteSupplierOrWarehouseCommandFormat + "\n\n"
                + deleteSupplierOrWarehouseSampleCommand + "\n\n" + deleteProductInstruction
                + "\n\n" + deleteProductCommandFormat + "\n\n" + deleteProductSampleCommand
                + "\n\n" + HELP_MESSAGE_FOR_USER_GUIDE;
        return deleteHelpMessage;
    }

    private String generateHelpEditMessage() {
        String editDescriptionMessage = "Edit\nEdits a warehouse or supplier at specified index."
                + " Name, Phone, Remarks of specified supplier and warehouse can be edited."
                + " In addition, a warehouse's address and a supplier's email can be edited as well. "
                + " Note that no two warehouses or two suppliers can share the same name in CLI-nic.";
        String editCommandFormat = "edit ct/TYPE i/INDEX [n/NAME] [p/PHONE] [r/REMARK] [addr/ADDRESS] "
                + "[e/EMAIL]";
        String editCommandSpecialInstructions = "addr/ADDRESS prefix can only be used for Warehouses "
                + "and e/EMAIL prefix can only be used for Suppliers. \nAt least one field has to be"
                + " specified in the edit command.\nEdited supplier or warehouse must be different"
                + " from one started with.";
        String editCommandSupplierSample = "edit ct/s i/1 n/Alice p/85236417 r/Largest Supplier"
                + " e/alicekoh@example.com";
        String editCommandWarehouseSample = "edit ct/w i/2 n/Bob p/67851234 r/Largest Warehouse"
                + " addr/Jurong Street 11";
        String editHelpMessage = HELP_MESSAGE_FOR_COMMAND_FORMAT + "\n\n" + editDescriptionMessage
                + "\n\n" + editCommandFormat + "\n\n"
                + editCommandSpecialInstructions + "\n\n" + editCommandSupplierSample + "\n\n"
                + editCommandWarehouseSample + "\n\n" + HELP_MESSAGE_FOR_USER_GUIDE;
        return editHelpMessage;
    }

    private String generateHelpExitMessage() {
        return "Exit\n\nExits the program.\n\nSample Command:\nexit";
    }

    private String generateHelpFindMessage() {
        String findDescription = "Find\nFinds all suppliers or warehouses whose name,"
                + " remark and/or products sold/stored matches the provided argument keywords.";
        String findCommandFormat = "Command format: \nfind ct/TYPE [n/NAME...]"
                + " [pd/PRODUCT_NAME...] [r/REMARK...]\n"
                + "TYPE should be one of these values: s / w.\n"
                + "At least one of the name, product or remark prefix must be provided.\n"
                + "Keywords specified are case-insensitive.";
        String findSampleCommand = "Sample Command: \n"
                + "find ct/s pd/panadol \nfind ct/w pd/face mask r/biggest";
        String findHelpMessage = HELP_MESSAGE_FOR_COMMAND_FORMAT + "\n\n"
                + findDescription + "\n\n"
                + findCommandFormat + "\n\n"
                + findSampleCommand + "\n\n" + HELP_MESSAGE_FOR_USER_GUIDE;
        return findHelpMessage;
    }

    private String generateHelpListMessage() {
        return "List\n\nLists all suppliers and warehouses.\n\nSample Command:\nlist";
    }

    private String generateHelpViewMessage() {
        String viewWarehouseOrSupplier = "View\nShows the supplier/warehouse at the specified index with"
                + " their relevant information e.g. products associated with the supplier/warehouse, "
                + "address etc.";
        String viewCommandFormat = "Command format: \nview ct/TYPE i/INDEX\n"
            + "The TYPE specified should be one of these values: s or w representing supplier or warehouse.\n"
            + "The supplier/warehouse INDEX specified should be within range of the supplier/warehouse "
            + "list, corresponding to the number on the list.";
        String viewSampleCommand = "view ct/s 1\nview ct/w 2";
        String viewCommandHelpMessage = HELP_MESSAGE_FOR_COMMAND_FORMAT + "\n\n"
            + viewWarehouseOrSupplier + "\n\n"
            + viewCommandFormat + "\n\n" + viewSampleCommand + "\n\n" + HELP_MESSAGE_FOR_USER_GUIDE;
        return viewCommandHelpMessage;
    }

    private String generateHelpUpdateMessage() {
        String updateProductInstruction = "Update\nIf the product does not exist for that warehouse/supplier,"
                + " it will associate the new product (optionally with quantity/tags) with the"
                + " warehouse/supplier. Otherwise, it will update the existing product in the"
                + " warehouse/supplier with the new quantity/tags. If the product already exists,"
                + " at least one optional argument has to be supplied.";
        String updateProductCommandFormat = "Command format: \n"
                + "update ct/TYPE n/ENTITY_NAME pd/PRODUCT_NAME [q/QUANTITY] [t/TAG]";
        String updateProductSampleCommand = "Sample Command: \n"
                + "update ct/w n/WarehouseA pd/Panadol q/10 t/fever";
        String updateCommandHelpMessage = HELP_MESSAGE_FOR_COMMAND_FORMAT + "\n\n"
                + updateProductInstruction + "\n\n"
                + updateProductCommandFormat + "\n\n"
                + updateProductSampleCommand + "\n\n" + HELP_MESSAGE_FOR_USER_GUIDE;
        return updateCommandHelpMessage;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof HelpCommand // instanceof handles nulls
                && commandArgument.equals(((HelpCommand) other).commandArgument)); // state check
    }
}
