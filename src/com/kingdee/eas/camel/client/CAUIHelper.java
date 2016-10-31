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
	 * ������ʹ�ý������ķ�ʽ��ui.
	 * 
	 * @param parentUI
	 * @param className
	 * 		��Ҫ�򿪵�ui����.
	 * @param type
	 * 		��������
	 * @param oprtState
	 * 		������ʲô״̬��
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
	 * ������ʹ�ý������ķ�ʽ��ui. 
	 * <p>Ĭ�ϴ򿪷�ʽΪ<code>UIFactoryName.MODEL</code></p>
	 * 
	 * @param parentUI
	 * @param className
	 * 		��Ҫ�򿪵�ui����.
	 * @param oprtState
	 * 		������ʲô״̬��
	 * @see OprtState
	 * @see UIFactoryName
	 * @see #showLongTimeDialog(CoreUI, String, String, String)
	 */
	public static void showLongTimeDialog4Model(final CoreUI parentUI, final String className, final String oprtState) throws Exception {
		showLongTimeDialog(parentUI, className, UIFactoryName.MODEL, oprtState);
	}
	
	/**
	 * ��������һ���µ�window����.
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
	 * ��������һ���µ�window����.
	 * 
	 * @param parentUI ������
	 * @param clazz �򿪵�Ŀ�����
	 * @param oprtState �򿪷�ʽ
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
	 * ��������һ��ָ����ui.
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
