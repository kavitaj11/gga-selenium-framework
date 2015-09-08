package com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.complex.table;

import com.ggasoftware.jdi_ui_tests.core.utils.common.LinqUtils;
import com.ggasoftware.jdi_ui_tests.core.utils.map.MapArray;
import com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.interfaces.common.IText;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.Collection;
import java.util.List;

import static com.ggasoftware.jdi_ui_tests.core.settings.JDISettings.asserter;
import static java.lang.String.format;

/**
 * Created by 12345 on 26.10.2014.
 */
class Columns extends TableLine {
    public Columns() {
        haveHeader = true;
        elementIndex = ElementIndexType.Nums;
    }

    protected List<WebElement> getHeadersAction() {
        return table.getWebElement().findElements(By.xpath(".//th"));
    }

    private RuntimeException throwColsException(String colName, Exception ex) {
        return asserter.exception(format("Can't Get Column '%s'. Exception: %s", colName, ex));
    }
    public final MapArray<String, ICell> getRow(String rowName) {
        try {
            return cellsToRow(LinqUtils.select(headers(), colName -> table.cell(new Column(colName), new Row(rowName))));
        }
        catch (Exception ex) { throw throwColsException(rowName, ex); }
    }
    public final MapArray<String, String> getRowAsText(String rowName) {
        return getRow(rowName).toMapArray(IText::getText);
    }

    public MapArray<String, ICell> cellsToRow(Collection<ICell> cells) {
        return new MapArray<>(cells,
                cell -> headers()[cell.columnNum() - 1],
                cell -> cell);
    }

    public MapArray<String, ICell> getRow(int rowNum) {
        int colsCount = -1;
        if (count > 0)
            colsCount = count;
        else if (headers != null && (headers.length > 0))
            colsCount = headers.length;
        if (colsCount == -1)
            colsCount = headers().length;
        if (colsCount > 0 && colsCount < rowNum)
            throw asserter.exception(format("Can't Get Column '%s'. [num] > ColumnsCount(%s).", rowNum, colsCount));
        try {
            return new MapArray<>(count(),
                    colNum -> headers()[colNum],
                    colNum -> table.cell(new Column(colNum+1), new Row(rowNum)));
        }
        catch (Exception ex) { throw throwColsException(rowNum + "", ex); }
    }
    public final MapArray<String, String> getRowAsText(int rowNum) {
        return getRow(rowNum).toMapArray(IText::getText);
    }

    public MapArray<String, MapArray<String, ICell>> get() {
        MapArray<String, MapArray<String, ICell>> cols = new MapArray<>();
        for(String columnName : headers()) {
            MapArray<String, ICell> row = new MapArray<>();
            for (String rowName : table.rows().headers())
                row.add(rowName, table.cell(new Column(columnName), new Row(rowName)));
            cols.add(columnName, row);
        }
        return cols;
    }
}