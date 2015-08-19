package com.ggasoftware.jdi_ui_tests.core.settings;

import com.ggasoftware.jdi_ui_tests.core.elements.base.IDriver;
import com.ggasoftware.jdi_ui_tests.implementations.elements.selenium.base.SlmDriver;
import com.ggasoftware.jdi_ui_tests.utils.linqInterfaces.JFuncT;
import com.ggasoftware.jdi_ui_tests.utils.map.MapArray;
import static com.ggasoftware.jdi_ui_tests.implementations.elements.selenium.DriverTypes.CHROME;
import static com.ggasoftware.jdi_ui_tests.implementations.elements.selenium.base.SlmDriver.isSupported;
import static com.ggasoftware.jdi_ui_tests.core.settings.JDISettings.asserter;
import static com.ggasoftware.jdi_ui_tests.utils.usefulUtils.TryCatchUtil.tryGetResult;
import static java.lang.String.format;

/**
 * Created by Roman_Iovlev on 8/10/2015.
 */
public abstract class DriverFactory {
    private static String currentDriverName = "";
    public static String getCurrentDriverName() { return currentDriverName; }
    private static MapArray<String, JFuncT<IDriver>> drivers = new MapArray<>();
    private static MapArray<String, IDriver> runDrivers = new MapArray<>();

    public static void registerDriver(JFuncT<IDriver> driver) {
        registerDriver(getDriverName("Driver"), driver);
    }
    public static void registerDriver(String driverName, JFuncT<IDriver> driver) {
        if (!drivers.add(getDriverName(driverName), driver))
            throw asserter.exception(format("Can't register Webdriver '%s'. Driver with same name already registered", driverName));
        currentDriverName = driverName;
    }
    public static void registerDriver(String driverName) {
        if (!isSupported(driverName))
            throw asserter.exception(format("Can't register Web Driver '%s'. Driver with same name already registered", driverName));
        String newDriverName = getDriverName(driverName);
        drivers.add(newDriverName, () -> new SlmDriver(driverName));
        currentDriverName = newDriverName;
    }

    // GET DRIVER
    public static Object getDriver() {
        if (!currentDriverName.equals(""))
            return getDriver(currentDriverName);
        registerDriver(CHROME.toString(), () -> new SlmDriver(CHROME));
        return getDriver(CHROME.toString());
    }

    private static String getDriverName(String driverName) {
        int numerator = 2;
        String name = driverName;
        while (drivers.keys().contains(name))
            name = name + numerator++;
        return name;
    }

    public static Object getDriver(String driverName) {
        try {
            if (runDrivers.keys().contains(driverName))
                return runDrivers.get(driverName).get();
            IDriver resultDriver = tryGetResult(() -> drivers.get(driverName).invoke());
            runDrivers.add(driverName, resultDriver);
            if (resultDriver == null)
                throw asserter.exception(format("Can't get driver '%s'. This Driver name not registered", driverName));
            return resultDriver.get();
        } catch (Exception ex) { throw asserter.exception("Can't get driver"); }
    }

    public static void reopenDriver() {
        reopenDriver(currentDriverName);
    }
    public static void reopenDriver(String name) {
        if (runDrivers.keys().contains(name)) {
            runDrivers.get(name).close();
            runDrivers.removeByKey(name);
        }
        if (drivers.keys().contains(name))
            getDriver(name);
    }
    public static void switchToDriver(String driverName) {
        if (!drivers.keys().contains(driverName))
            throw asserter.exception(format("Can't switch to Webdriver '%s'. This Driver name not registered", driverName));
        currentDriverName = driverName;
    }

}