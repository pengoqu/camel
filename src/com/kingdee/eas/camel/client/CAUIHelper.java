package com.kingdee.eas.camel.client;

import java.util.Map;

import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.eas.base.permission.client.longtime.ILongTimeTask;
import com.kingdee.eas.base.permission.client.longtime.LongTimeDialog;
import com.kingdee.eas.base.permission.client.util.UITools;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.framework.client.CoreUI;

public class CAUIHelper {
	/**
	 * 
	 * 描述：使用进度条的方式打开ui.
	 * 
	 * @param parentUI
	 * @param className
	 * 		需要打开的ui类名.
	 * @param type
	 * 		窗口类型
	 * @param oprtState
	 * 		窗口已什么状态打开
	 * @see OprtState
	 * @see UIFactoryName
	 */
	public static void showLongTimeDialog(final CoreUI parentUI, final String className, final String type, final String oprtState) throws Exception {
		LongTimeDialog dialog = UITools.getDialog(parentUI);
        dialog.setLongTimeTask(new ILongTimeTask() {

			public void afterExec(Object result) throws Exception {
				UITools.showObject(result);
			}

			public Object exec() throws Exception {
				UIContext uiContext = new UIContext(parentUI);
				return UIFactory.createUIFactory(type).create(className, uiContext, null, oprtState);
			}
        	
        });
        dialog.show();
	}
	
	/**
	 * 
	 * 描述：使用进度条的方式打开ui. 
	 * <p>默认打开方式为<code>UIFactoryName.MODEL</code></p>
	 * 
	 * @param parentUI
	 * @param className
	 * 		需要打开的ui类名.
	 * @param oprtState
	 * 		窗口已什么状态打开
	 * @see OprtState
	 * @see UIFactoryName
	 * @see #showLongTimeDialog(CoreUI, String, String, String)
	 */
	public static void showLongTimeDialog4Model(final CoreUI parentUI, final String className, final String oprtState) throws Exception {
		showLongTimeDialog(parentUI, className, UIFactoryName.MODEL, oprtState);
	}
	
	/**
	 * 描述：打开一个新的window窗口.
	 * @param parentUI
	 * @param clazz
	 * @param params
	 * @param oprtState
	 * @see #openUI(CoreUI, Class, String, String)
	 */
	public static void openWin(CoreUI parentUI, Class clazz, Map params, String oprtState) throws Exception {
		IUIWindow win = UIFactory.createUIFactory(UIFactoryName.NEWWIN).create(clazz.getName(), params, null, oprtState);
		win.show();
	}
	
	/**
	 * 描述：打开一个新的window窗口.
	 * 
	 * @param parentUI 父窗口
	 * @param clazz 打开的目标界面
	 * @param oprtState 打开方式
	 * @throws Exception
	 */
	public static void openWin(CoreUI parentUI, Class clazz, String oprtState) throws Exception {
		UIContext uiContext = new UIContext(parentUI);
		openWin(parentUI, clazz, uiContext, oprtState);
	}
	
	public static void openDialog(CoreUI parentUI, Class clazz, String oprtState) throws Exception {
		UIContext uiContext = new UIContext(parentUI);
		openDialog(parentUI, clazz, uiContext, oprtState);
	}
	
	public static void openDialog(CoreUI parentUI, Class clazz, Map params, String oprtState) throws Exception {
		IUIWindow win = UIFactory.createUIFactory(UIFactoryName.MODEL).create(clazz.getName(), params, null, oprtState);
		win.show();
	}
	
	public static void openTabWin(CoreUI parentUI, Class clazz, String oprtState) throws Exception {
		UIContext uiContext = new UIContext(parentUI);
		IUIWindow win = UIFactory.createUIFactory(UIFactoryName.NEWTAB).create(clazz.getName(), uiContext, null, oprtState);
		win.show();
	}
	
	public static void openTabWin(CoreUI parentUI, Class clazz, Map params, String oprtState) throws Exception {
		UIContext uiContext = new UIContext(parentUI);
		uiContext.putAll(params);
		IUIWindow win = UIFactory.createUIFactory(UIFactoryName.NEWTAB).create(clazz.getName(), uiContext, null, oprtState);
		win.show();
	}
	
	/**
	 * 描述：打开一个指定的ui.
	 * 
	 * @param parentUI
	 * @param clazz
	 * @param type
	 * @param oprtState
	 * @see UIFactoryName
	 * @see OprtState
	 */
	public static void openUI(CoreUI parentUI, Class clazz, String type, String oprtState) throws Exception {
		UIContext uiContext = new UIContext(parentUI);
		IUIWindow win = UIFactory.createUIFactory(type).create(clazz.getName(), uiContext, null, oprtState);
		win.show();
	}
	
	public static void openUI(CoreUI parentUI, Class clazz, String type, Map params, String oprtState) throws Exception {
		UIContext uiContext = new UIContext(parentUI);
		IUIWindow win = UIFactory.createUIFactory(type).create(clazz.getName(), uiContext, null, oprtState);
		win.show();
	}
}
