/**
 * *************************************************************************
 * Copyright (C) 2014 GGA Software Services LLC
 * <p>
 * This file may be distributed and/or modified under the terms of the
 * GNU General Public License version 3 as published by the Free Software
 * Foundation.
 * <p>
 * This file is provided AS IS with NO WARRANTY OF ANY KIND, INCLUDING THE
 * WARRANTY OF DESIGN, MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE.
 * <p>
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, see <http://www.gnu.org/licenses>.
 * *************************************************************************
 */
package com.ggasoftware.uitest.control.new_controls.complex;

import com.ggasoftware.uitest.control.interfaces.complex.ICheckList;
import org.openqa.selenium.By;

/**
 * Select control implementation
 *
 * @author Alexeenko Yan
 * @author Belousov Andrey
 */
public class CheckList<TEnum extends Enum, P> extends MultiSelector<TEnum, P> implements ICheckList<TEnum> {
    public CheckList() {
        super();
    }

    public CheckList(By optionsNamesLocator) {
        super(optionsNamesLocator);
    }

    public CheckList(By optionsNamesLocator, By allOptionsNamesLocator) {
        super(optionsNamesLocator, allOptionsNamesLocator);
    }
}
