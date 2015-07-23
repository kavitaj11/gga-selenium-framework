/****************************************************************************
 * Copyright (C) 2014 GGA Software Services LLC
 *
 * This file may be distributed and/or modified under the terms of the
 * GNU General Public License version 3 as published by the Free Software
 * Foundation.
 *
 * This file is provided AS IS with NO WARRANTY OF ANY KIND, INCLUDING THE
 * WARRANTY OF DESIGN, MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, see <http://www.gnu.org/licenses>.
 ***************************************************************************/
package com.ggasoftware.uitest.control;

import com.ggasoftware.uitest.control.base.asserter.TestNGAsserter;
import com.ggasoftware.uitest.control.interfaces.common.IInput;
import com.ggasoftware.uitest.control.new_controls.common.Text;
import com.ggasoftware.uitest.utils.ReporterNGExt;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

import static com.ggasoftware.uitest.control.base.asserter.TestNGAsserter.asserter;

/**
 * Text Field control implementation
 *
 * @author Alexeenko Yan
 * @author Shubin Konstantin
 * @author Zharov Alexandr
 */
public class Input<ParentPanel> extends Text<ParentPanel> implements IInput<ParentPanel> {

    //constructors

    /**
     * Initializes element with given locator. Locates own properties of the element by class name, takes given locator and tries
     * to initialize.
     *
     * @param name        - Input name
     * @param locator     - start it with locator type "id=", "css=", "xpath=" and etc. Locator without type is assigned to xpath
     * @param parentPanel - Parent instance
     */
    public Input(String name, String locator, ParentPanel parentPanel) {
        super(name, locator, parentPanel);
    }
    public Input() { super(); }
    public Input(By byLocator) { super(byLocator); }

    protected void setValueAction(String value) { newInput(value); }
    @Override
    protected String getTextAction() { return getWebElement().getAttribute("value"); }
    protected void inputAction(String text) { getWebElement().sendKeys(text); }
    protected void clearAction() { getWebElement().clear(); }
    protected void focusAction() { getWebElement().click(); }

    public final void input(String text) {
        doJAction("Input text '" + text + "' in text field",
                () -> setValueRule.invoke(text, this::inputAction));
    }
    public final void newInput(String text) {
        asserter.silentException(() -> setValueRule.invoke(text, t -> {
            clear();
            input(t);
        }));
    }

    public final void setValue(String value) {
        doJAction("Set value", () -> setValueRule.invoke(value, this::setValueAction));
    }
    @Override
    public final ParentPanel focus() {
        doJAction("Focus on text field", this::focusAction);
        return parent;
    }


    /**
     * !!! Use newInput() instead
     * Type text to the Input field
     *
     * @param text - text for Input field
     * @return Parent instance
     */
    @Deprecated
    public ParentPanel setText(String text) {
        newInput(text);
        return super.parent;
    }

    /**
     * Type text to the Input field with secure log.
     *
     * @param text - text for Input field
     * @return Parent instance
     */
    public ParentPanel setTextSecure(String text) {
        ReporterNGExt.logAction(this, getParentClassName(), String.format("setTextSecure - %s", text.replaceAll("[^']", "*")));
        WebElement webEl = getWebElement();
        webEl.click();
        webEl.clear();
        webEl.click();
        super.sendKeysSecure(text);
        return super.parent;
    }

    /**
     * Clear the value from the Input field
     *
     * @return Parent instance
     */
    public ParentPanel clear() {
        ReporterNGExt.logAction(this, getParentClassName(), "Clear");
        getWebElement().clear();
        return super.parent;
    }

    /**
     * Use this method to simulate typing into an element, which may set its value.
     *
     * @param keysToSend - CharSequence to send
     * @return Parent instance
     */
    public ParentPanel sendKeys(CharSequence... keysToSend) {
        return super.sendKeys(keysToSend);
    }

    /**
     * Use this method to simulate enter key press into an element.
     *
     * @return Parent instance
     */
    public ParentPanel pressEnter() {
        return super.sendKeys(Keys.ENTER);
    }

    /**
     * Use this method to simulate send keys to the element.
     *
     * @param sendKeys - e.g. sendKeys(Keys.ARROW_DOWN)
     * @return Parent instance
     */
    public ParentPanel sendKeys(Keys sendKeys) {
        return super.sendKeys(sendKeys);
    }

}