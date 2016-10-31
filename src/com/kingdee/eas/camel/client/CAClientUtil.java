package com.kingdee.eas.camel.client;


import java.awt.Component;
import java.awt.Container;
import java.awt.Rectangle;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.RowSetMetaData;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.ctrl.kdf.util.style.Styles;
import com.kingdee.bos.ctrl.swing.KDContainer;
import com.kingdee.bos.ctrl.swing.KDDatePicker;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDPanel;
import com.kingdee.bos.ctrl.swing.KDWorkButton;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.ItemAction;
import com.kingdee.bos.workflow.ProcessInstInfo;
import com.kingdee.bos.workflow.service.ormrpc.EnactmentServiceFactory;
import com.kingdee.bos.workflow.service.ormrpc.IEnactmentService;
import com.kingdee.eas.camel.CAArrayUtils;
import com.kingdee.eas.camel.constant.CADateConstant;
import com.kingdee.eas.camel.constant.CANumberConstant;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.framework.client.multiDetail.DetailPanel;
import com.kingdee.eas.scm.common.client.helper.FormattedEditorFactory;
import com.kingdee.eas.scm.common.util.PrecisionUtil;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.StringUtils;


public final class CAClientUtil {
	private static final String resPath = "com.kingdee.eas.fm.common.FMResource";
	
	public static String getFormatString(int precision) {
        if(precision == 0)
            return "%R-{#,##0}f";
        StringBuffer buffer = new StringBuffer();
        if(precision == 0)
            buffer.append("#");
        else
            buffer.append("0.");
        for(int i = 0; i < precision; i++)
            buffer = buffer.append("0");

        StringBuffer formatString = new StringBuffer();
        formatString.append("%r-[=]{#,##").append(buffer).append("}f");
        return formatString.toString();
    }
	
	
	/**
	 * ganzhong_dai 获取对应选择行的ID集合
	 * @param table
	 * @param columnName
	 * @return
	 */
	 public static List getTableColumnValue(KDTable table, String columnName)
	    {
	        List list = new ArrayList();
	        int i = 0;
	        int selectRows[] = KDTableUtil.getSelectedRows(table);
	        for(int n =selectRows.length; i < n; i++){
	        	int rowSize = selectRows[i];
	            list.add(getTableSelectCellValue(table, columnName, rowSize));
	        }

	        return list;
	    }

	 
	 public static Object getTableSelectCellValue(KDTable table, String columnName, int indexRow)
	    {
	        if(table != null && columnName != null && indexRow >= 0)
	        {
	            IRow iRow = table.getRow(indexRow);
	            ICell iCell = iRow.getCell(columnName);
	            if(iCell != null)
	                return iCell.getValue();
	        }
	        return null;
	    }

	 
	 
	 /**
     * 描述： 在detailPanel 上添加自定义的 <code>KDWorkButton<code>. 默认添加到btnRemoveLines 位置<br/>
     * note:
     * 		<blockquote>
     * 		controlPanel.add(btnAddnewLine, new com.kingdee.bos.ctrl.swing.KDLayout.Constraints(rect.width - 86, 5, 22, 19, 9));
        	controlPanel.add(btnInsertLine, new com.kingdee.bos.ctrl.swing.KDLayout.Constraints(rect.width - 59, 5, 22, 19, 9));
        	controlPanel.add(btnRemoveLines, new com.kingdee.bos.ctrl.swing.KDLayout.Constraints(rect.width - 32, 5, 22, 19, 9));
        	
        	btnAddnewLine.setToolTipText(EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Tip_AddLine"));
	        btnAddnewLine.setIcon(EASResource.getIcon("imgTbtn_addline"));
	        btnInsertLine.setToolTipText(EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Tip_InsertLine"));
	        btnInsertLine.setIcon(EASResource.getIcon("imgTbtn_insert"));
	        btnRemoveLines.setToolTipText(EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Tip_RemoveLine"));
	        btnRemoveLines.setIcon(EASResource.getIcon("imgTbtn_deleteline"));
        	</blockquote>
     * @param detailPanel
     * @param tb
     * @param customButton
     */
    public static void addCustomDetailPanel(DetailPanel detailPanel, KDTable tb, KDWorkButton customButton) {
		addCustomDetailPanel(detailPanel, tb, customButton, 32, 5, 22, 19, 9);
    }
    
    /**
     * 描述： 在<code>DetailPanel</code>上添加自定义按钮
     * @param detailPanel
     * @param customButton
     * @param arg1
     * @param arg2
     * @param arg3
     * @param arg4
     * @param arg5
     */
    public static void addCustomDetailPanel(DetailPanel detailPanel, KDTable tb, KDWorkButton customButton, int arg1, int arg2, int arg3, int arg4, int arg5) {
    	Container container = detailPanel.getRemoveLinesButton().getParent();
		
    	Rectangle rect = tb.getBounds();
    	
		container.add(customButton, new com.kingdee.bos.ctrl.swing.KDLayout.Constraints(
				rect.width - arg1, arg2, arg3, arg4, arg5));
    }
    
    /** 
     *  
     * 根据用户生日计算年龄 
     * */
    public static int getAgeByBirthday(Date birthday) {
    	Calendar cal = Calendar.getInstance();	
    	if (cal.before(birthday)) {		
    		throw new IllegalArgumentException("The birthDay is before Now.It's unbelievable!");
    		}	
    	int yearNow = cal.get(Calendar.YEAR);
    	int monthNow = cal.get(Calendar.MONTH) + 1;	
    	int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);	
    	cal.setTime(birthday);	
    	int yearBirth = cal.get(Calendar.YEAR);
    	int monthBirth = cal.get(Calendar.MONTH) + 1;	
    	int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);	
    	int age = yearNow - yearBirth;	if (monthNow <= monthBirth) {		
    		if (monthNow == monthBirth) {	
    			// monthNow==monthBirth 		
    			if (dayOfMonthNow < dayOfMonthBirth) {	
    				age--;	
    				}	
    			}else {			// monthNow>monthBirth 		
    				age--;		
    			}
    		}	
    	return age;
    }
    
    /**
     * 描述: 移除<code>DetailPanel</code>的相关按钮 <br/>
     * <ul>
     * 		<li>btnAddnewLine
     * 		<li>btnInsertLine
     * 		<li>btnRemoveLines
     * </ul>
     * @param detailPanel
     */
    public static void removeDetaiPanelButton(DetailPanel detailPanel) {
    	detailPanel.getAddNewLineButton().setVisible(false);
    	detailPanel.getInsertLineButton().setVisible(false);
    	detailPanel.getRemoveLinesButton().setVisible(false);
    }
    
    /**
     * 描述: 显示<code>DetailPanel</code>的相关按钮 <br/>
     * <ul>
     * 		<li>btnAddnewLine
     * 		<li>btnInsertLine
     * 		<li>btnRemoveLines
     * </ul>
     * @param detailPanel
     */
    public static void addDetaiPanelButton(DetailPanel detailPanel) {
    	detailPanel.getAddNewLineButton().setVisible(true);
    	detailPanel.getInsertLineButton().setVisible(true);
    	detailPanel.getRemoveLinesButton().setVisible(true);
    }
    
    /**
     * 描述: 设置<code>DetailPanel</code>的相关按钮不可用 <br/>
     * @param detailPanel
     */
    public static void disableDetailPanelButton(DetailPanel detailPanel) {
    	detailPanel.getAddNewLineButton().setEnabled(false);
    	detailPanel.getInsertLineButton().setEnabled(false);
    	detailPanel.getRemoveLinesButton().setEnabled(false);
    }
    
    /**
     * 描述: 设置<code>DetailPanel</code>的相关按钮可用 <br/>
     * @param detailPanel
     */
    public static void enableDetailPanelButton(DetailPanel detailPanel) {
    	detailPanel.getAddNewLineButton().setEnabled(true);
    	detailPanel.getInsertLineButton().setEnabled(true);
    	detailPanel.getRemoveLinesButton().setEnabled(true);
    }
    
    public static void setDetailPanelTitle(DetailPanel detailPanel, String titleName, int w) {
    	detailPanel.setTitle(titleName);
		Component[] comps = detailPanel.getComponents();
		for (int i = 0; comps != null && i < comps.length; i++) {
			if (comps[i] instanceof KDPanel
					&& "controlPanel".equals(comps[i].getName())) {
				KDPanel controlPanel = (KDPanel) comps[i];
				Component[] comps2 = controlPanel.getComponents();
				for (int j = 0; comps2 != null && j < comps.length; j++) {
					if (comps2[j] != null
							&& "kdDetailCon".equals(comps2[j].getName())) {
						KDContainer kdConTitle = (KDContainer) comps2[j];
						controlPanel.add(kdConTitle,new com.kingdee.bos.ctrl.swing.KDLayout.Constraints(0, 5, w, 19, 5));
					}
				}
			}
		}
    }
    
    /**
	 * 描述：设置detailPanel的标题
	 * @param detailPanel
	 * 		table相关联的panel
	 * @param titleName
	 * 		标题
	 */
	public static void setDetailPanelTitle(DetailPanel detailPanel, String titleName) {
		Rectangle rect = detailPanel.getBounds();
		setDetailPanelTitle(detailPanel, titleName, rect.width - 85);
	}
	
	public static void initPrecision(KDTable table, String[] keys, int precision, boolean isNegatived) {
		if (CAArrayUtils.isEmpty(keys)) {
			return;
		}
		KDTDefaultCellEditor editor = getNumberCellEditor(precision, isNegatived);
		
		IColumn column = null;
		for (int i = 0; i < keys.length; i++) {
			column = table.getColumn(keys[i]);
			
			if (null != column) {
				column.setEditor(editor);
			}
		}
	}
	
	/**
	 * 设置kdtable数字列精度
	 * @param precision 精度位数
	 * @param table   table名
	 * @param fieldNames  精度列
	 * @param isNegatived 正负数
	 * @deprecated
	 */
	public static void changeTableColPrecision(int precision, KDTable table, String fieldNames[], boolean isNegatived)
    {
        KDTDefaultCellEditor decimalEditor = FormattedEditorFactory.getBigDecimalCellEditor(precision, isNegatived);
        String colPrecision = PrecisionUtil.getFormatString(precision);
        ICell iCell = null;
        int rowCount = table.getRowCount() != -1 ? table.getRowCount() : table.getBody().size() - 1;
        int i = 0;
        for(int num = fieldNames.length; i < num; i++)
        {
            if(table.getColumn(fieldNames[i]) == null)
                continue;
            for(int j = 0; j < rowCount; j++)
            {
                iCell = table.getRow(j).getCell(fieldNames[i]);
                if(iCell != null)
                {
                    iCell.setEditor(decimalEditor);
                    iCell.getStyleAttributes().setNumberFormat(colPrecision);
                    BigDecimal qty = (BigDecimal)iCell.getValue();
                    qty = qty != null ? qty.setScale(precision, 4) : null;
                    iCell.setValue(qty);
                }
            }
        }
    }
	
	/**
	 * 设置kdtable数字列精度
	 * @param precision 精度位数
	 * @param table   table名
	 * @param fieldNames  精度列
	 * @param isNegatived 正负数
	 * @deprecated
	 */
	public static void changeTablePerJ(int precision, KDTable table, List list, boolean isNegatived)
    {
        KDTDefaultCellEditor decimalEditor = FormattedEditorFactory.getBigDecimalCellEditor(precision, isNegatived);
        String colPrecision = PrecisionUtil.getFormatString(precision);
        ICell iCell = null;
        int rowCount = table.getRowCount() != -1 ? table.getRowCount() : table.getBody().size() - 1;
        int i = 0;
        for(int num = list.size(); i < num; i++)
        {
            if(table.getColumn(list.get(i).toString()) == null)
                continue;
            for(int j = 0; j < rowCount; j++)
            {
                iCell = table.getRow(j).getCell(list.get(i).toString());
                if(iCell != null)
                {
                    iCell.setEditor(decimalEditor);
                    iCell.getStyleAttributes().setNumberFormat(colPrecision);
                    BigDecimal qty = (BigDecimal)iCell.getValue();
                    qty = qty != null ? qty.setScale(precision, 4) : null;
                    iCell.setValue(qty);
                }
            }
        }
    }
	
	/**
	 * 设置action可用性.
	 * 
	 * @param action
	 * @param enable
	 */
	public static void setActionEnable(ItemAction action, boolean enable) {
		if (null == action) {
			return;
		}
		action.setEnabled(enable);
		action.setVisible(enable);
	}
	
	/**
	 * <p>获取数量单元格编辑器.</p>
	 * 
	 * @return
	 */
	public static KDTDefaultCellEditor getNumberCellEditor() {
		KDFormattedTextField indexValue_TextField = new KDFormattedTextField();
		indexValue_TextField.setName("indexValue_TextField");
		indexValue_TextField.setVisible(true);
		indexValue_TextField.setEditable(true);
		indexValue_TextField.setHorizontalAlignment(4);
		indexValue_TextField.setDataType(1);
		indexValue_TextField.setMaximumValue(CANumberConstant.MAX_VALUE);
		indexValue_TextField.setMinimumValue(CANumberConstant.MIN_VALUE);
		indexValue_TextField.setSupportedEmpty(true);
		indexValue_TextField.setPrecision(2);
		indexValue_TextField.setRemoveingZeroInDispaly(false);
		indexValue_TextField.setRemoveingZeroInEdit(false);
		KDTDefaultCellEditor indexValue_CellEditor = new KDTDefaultCellEditor(indexValue_TextField);
		indexValue_CellEditor.setClickCountToStart(1);
		return indexValue_CellEditor;
	}
	
	public static KDTDefaultCellEditor getNumberCellEditor(int precision, boolean isNegatived) {
		KDFormattedTextField indexValue_TextField = new KDFormattedTextField();
		indexValue_TextField.setName("indexValue_TextField");
		indexValue_TextField.setVisible(true);
		indexValue_TextField.setEditable(true);
		indexValue_TextField.setHorizontalAlignment(4);
		indexValue_TextField.setDataType(1);
		indexValue_TextField.setMaximumValue(CANumberConstant.MAX_VALUE);
		indexValue_TextField.setMinimumValue(CANumberConstant.MIN_VALUE); 
		indexValue_TextField.setSupportedEmpty(true);
		indexValue_TextField.setPrecision(precision);
		indexValue_TextField.setNegatived(isNegatived);
		indexValue_TextField.setRemoveingZeroInDispaly(false);
		indexValue_TextField.setRemoveingZeroInEdit(false);
		KDTDefaultCellEditor indexValue_CellEditor = new KDTDefaultCellEditor(indexValue_TextField);
		indexValue_CellEditor.setClickCountToStart(1);
		return indexValue_CellEditor;
	}
	
	/**
	 * <p>获取日期单元格编辑器.</p>
	 * 
	 * @return
	 */
	public static KDTDefaultCellEditor getDateCellEditor() {
		KDDatePicker dateEditor = new KDDatePicker();
		dateEditor.setName("dateEditor");
		dateEditor.setVisible(true);
		dateEditor.setEditable(true);
		KDTDefaultCellEditor date_CellEditor = new KDTDefaultCellEditor(dateEditor);
		date_CellEditor.setClickCountToStart(1);
		return date_CellEditor;
	}	
	
	/**
     * 检查是否在工作流中
     * @return
     * @throws BOSException 
     */
    public static void checkSelectedWfState(Component comp, String id) throws BOSException {
		// 检查流程提交结果
        IEnactmentService service = EnactmentServiceFactory.createRemoteEnactService();
        ProcessInstInfo processInstInfo = null;
        ProcessInstInfo procInsts[] = service.getProcessInstanceByHoldedObjectId(id);
        int i = 0;
        for(int n = procInsts.length; i < n; i++)
            if(procInsts[i].getState().startsWith("open"))
                processInstInfo = procInsts[i];

        if(processInstInfo != null)
        {
        	MsgBox.showInfo(comp, "已进入工作流，不能执行此操作");
        	SysUtil.abort();
        }
    }
    
    public static boolean isEmpty(Object obj) {
	    if (obj instanceof String) {
	    	return StringUtils.isEmpty((String)obj);
	    }
	    if (obj instanceof Map) {
	    	Map map = (Map)obj;
	    	return (map.size() <= 0); 
	    }
	    if (obj instanceof Collection) {
	    	Collection c = (Collection)obj;
	    	return (c.size() <= 0);
	    }
	    return ((obj == null) ? true : isEmpty(obj.toString()));
    }
    
    public static Map getColumnsByRowSet(IRowSet rowSet)
    	throws SQLException {
	    if(rowSet == null)
	        return null;
	    RowSetMetaData rsmd = (RowSetMetaData)rowSet.getMetaData();
	    Map colMap = new HashMap();
	    int i = 1;
	    for(int size = rsmd.getColumnCount(); i < size + 1; i++) {
	        colMap.put(rsmd.getColumnName(i), rsmd.getColumnName(i));
	    }
	        
	    return colMap;
	}
    
    /**
     * 初始化列精度.
     * 
     * @param precision 精度
     * @param fields 列数组
     * @param rowSet 数据集
     * @param colMap
     * @throws SQLException
     */
    public static void iniColumnsPrecision(int precision, String fields[], IRowSet rowSet, Map colMap)
    	throws SQLException {
    	if(fields == null || rowSet == null || colMap == null)
    		return;
    	for(int i = 0; i < fields.length; i++)
    		if(colMap.containsKey(fields[i]))
    			iniColumnsPrecision(precision, fields[i], rowSet);

    }
    
    /**
     * <p>数据集对应列精度设置.</p>
     * 
     * @param precision 精度值
     * @param field 列名称
     * @param rowSet 
     * @throws SQLException
     */
    public static void iniColumnsPrecision(int precision, String field, IRowSet rowSet)
    	throws SQLException {
	    BigDecimal temp = rowSet.getBigDecimal(field);
	    if(temp != null) {
	        temp = temp.setScale(precision, 4);
	        rowSet.updateObject(field, temp);
	    }
	}
    
    /**
     * <p>格式化指定的列为日期类型(yyyy-MM-dd).</p>
     * 
     * @param table
     * @param key 列名
     */
    public static void formatTableDate(KDTable table, String key) {
    	formatTable(table, key, CADateConstant.PATTERN_DAY);
    }
    
    /**
     * <p>格式化指定的列为日期类型(yyyy-MM-dd HH:mm:ss).</p>
     * 
     * @param table
     * @param key 列名
     */
    public static void formatTableTimestamp(KDTable table, String key) {
    	formatTable(table, key, CADateConstant.PATTERN_TIMESTAMP);
    }
    
    /**
     * 格式化指定的列.
     * 
     * @param table
     * @param key 列名
     * @param format 
     */
    public static void formatTable(KDTable table, String key, String format) {
    	table.getColumn(key).getStyleAttributes().setNumberFormat(format);
    }
    
    /**
     * <p>格式化指定的列为数字类型(#,##0.00;-#,##0.00).</p>
     * 
     * @param table
     * @param key 列名
     */
    public static void formatTableNumber(KDTable table, String key) {
    	formatTableNumber(table, key, "#,##0.00;-#,##0.00");
    }
    
    /**
     * <p>格式化指定的列为数字类型</p>
     * 
     * @param table
     * @param key 列名
     * @param format
     */
    public static void formatTableNumber(KDTable table, String key, String format) {
    	table.getColumn(key).getStyleAttributes().setNumberFormat(format);
    	table.getColumn(key).getStyleAttributes().setHorizontalAlign(Styles.HorizontalAlignment.RIGHT);
    }
    
//    public static void formatTableNumber(KDTable table, String key, int precision) {
//    	formatTableNumber(table, key, com.kingdee.eas.base.core.util.PrecisionUtil.getFormatString(precision));
//    }
//    
//    public static void formatTableNumber(KDTable table, String[] keys, int precision) {
//    	if (null == keys || 0 == keys.length) {
//    		return;
//    	}
//    	for (int i = 0; i < keys.length; i++) {
//    		formatTableNumber(table, keys[i], precision);
//    	}
//    }
    
    /**
     * 设置列合并.
     * 根据列名
     * @param tbl
     * @param keys
     */
    public static void setMergeColumn(KDTable tbl, String[] keys) {
        String mergeColumnKeys[] = keys;
        
        if(mergeColumnKeys != null && mergeColumnKeys.length > 0) {
        	tbl.checkParsed();
        	tbl.getGroupManager().setGroup(true);
            for(int i = 0; i < mergeColumnKeys.length; i++) {
            	tbl.getColumn(mergeColumnKeys[i]).setGroup(true);
            	tbl.getColumn(mergeColumnKeys[i]).setMergeable(true);
            }
        }
    }
    
    
    
    /**
     * 设置列合并.
     * 根据第几列
     * @param tbl
     * @param keys
     */ 
    public static void setMergeColumns(KDTable tbl, int[] keys) {
        int mergeColumnKeys[] = keys;
        
        if(mergeColumnKeys != null && mergeColumnKeys.length > 0) {
        	tbl.checkParsed();
        	tbl.getGroupManager().setGroup(true);
            for(int i = 0; i < mergeColumnKeys.length; i++) {
            	tbl.getColumn(mergeColumnKeys[i]).setGroup(true);
            	tbl.getColumn(mergeColumnKeys[i]).setMergeable(true);
            }
        }
    }
    /**
     * 更新UI标题, 追加[查看]、[修改]、[新增]字样.
     * 
     * @param ui
     * @param title
     */
    public static void updateUITitle(CoreUIObject ui, String title) {
	    String strTitle = title;
	    if (ui.getOprtState().equals(OprtState.VIEW)) {
	    	ui.setUITitle(strTitle + " - " + EASResource.getString(resPath, "view"));
	    } else if (ui.getOprtState().equals(OprtState.EDIT)) {
	    	ui.setUITitle(strTitle + " - " + EASResource.getString(resPath, "edit"));
	    } else if (ui.getOprtState().equals(OprtState.ADDNEW)) {
	    	ui.setUITitle(strTitle + " - " + EASResource.getString(resPath, "new"));
	    }
    }
}
