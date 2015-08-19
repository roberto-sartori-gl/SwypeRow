package com.robertogl.BlackTwitter; 

import android.content.res.XModuleResources;
import de.robv.android.xposed.IXposedHookInitPackageResources;
import de.robv.android.xposed.IXposedHookZygoteInit;
import de.robv.android.xposed.callbacks.XC_InitPackageResources.InitPackageResourcesParam;

public class Main implements IXposedHookZygoteInit, IXposedHookInitPackageResources {
    private static String MODULE_PATH = null;

    @Override
    public void initZygote(StartupParam startupParam) throws Throwable {
        MODULE_PATH = startupParam.modulePath;
    }
   
 
    @Override
    public void handleInitPackageResources(InitPackageResourcesParam resparam) throws Throwable {
        if (!resparam.packageName.equals("com.twitter.android"))
            return;

        XModuleResources modRes = XModuleResources.createInstance(MODULE_PATH, resparam.res);
        resparam.res.setReplacement("com.twitter.android", "color", "white", modRes.fwd(R.color.white));
        resparam.res.setReplacement("com.twitter.android", "color", "faint_transparent_white", modRes.fwd(R.color.faint_transparent_white));
        resparam.res.setReplacement("com.twitter.android", "color", "common_signin_btn_default_background", modRes.fwd(R.color.common_signin_btn_default_background));
        resparam.res.setReplacement("com.twitter.android", "color", "primary_text", modRes.fwd(R.color.primary_text));
        resparam.res.setReplacement("com.twitter.android", "color", "secondary_text", modRes.fwd(R.color.secondary_text));
        resparam.res.setReplacement("com.twitter.android", "color", "text", modRes.fwd(R.color.text));
        resparam.res.setReplacement("com.twitter.android", "color", "btn_default_fill", modRes.fwd(R.color.btn_default_fill));
		/*resparam.res.setReplacement("com.twitter.android", "color", "list_margin_bg", modRes.fwd(R.color.list_margin_bg));
		resparam.res.setReplacement("com.twitter.android", "color", "list_bg", modRes.fwd(R.color.list_bg));
		resparam.res.setReplacement("com.twitter.android", "color", "focused_bg", modRes.fwd(R.color.focused_bg));
		resparam.res.setReplacement("com.twitter.android", "color", "placeholder_bg", modRes.fwd(R.color.placeholder_bg));
		resparam.res.setReplacement("com.twitter.android", "color", "media_border", modRes.fwd(R.color.media_border));
		resparam.res.setReplacement("com.twitter.android", "color", "light_gray", modRes.fwd(R.color.light_gray));
		resparam.res.setReplacement("com.twitter.android", "color", "light_blue", modRes.fwd(R.color.light_blue));
		resparam.res.setReplacement("com.twitter.android", "color", "image_overlay", modRes.fwd(R.color.image_overlay));
		resparam.res.setReplacement("com.twitter.android", "color", "actionbar_divider", modRes.fwd(R.color.actionbar_divider));
	    resparam.res.setReplacement("com.twitter.android", "color", "highlights_underlay", modRes.fwd(R.color.highlights_underlay));
	    resparam.res.setReplacement("com.twitter.android", "color", "deep_transparent_twitter_blue", modRes.fwd(R.color.deep_transparent_twitter_blue));
        resparam.res.setReplacement("com.twitter.android", "color", "deep_transparent_gray", modRes.fwd(R.color.deep_transparent_gray));
		resparam.res.setReplacement("com.twitter.android", "color", "deep_gray", modRes.fwd(R.color.deep_gray));
		resparam.res.setReplacement("com.twitter.android", "color", "deep_blue", modRes.fwd(R.color.deep_blue));
		resparam.res.setReplacement("com.twitter.android", "color", "deep_transparent_black", modRes.fwd(R.color.deep_transparent_black));
		resparam.res.setReplacement("com.twitter.android", "color", "common_signin_btn_light_text_focused", modRes.fwd(R.color.common_signin_btn_light_text_focused));
		resparam.res.setReplacement("com.twitter.android", "color", "common_signin_btn_light_text_disabled", modRes.fwd(R.color.common_signin_btn_light_text_disabled));
		resparam.res.setReplacement("com.twitter.android", "color", "common_signin_btn_light_text_default", modRes.fwd(R.color.common_signin_btn_light_text_default));
		resparam.res.setReplacement("com.twitter.android", "color", "common_signin_btn_default_background", modRes.fwd(R.color.common_signin_btn_default_background));
        resparam.res.setReplacement("com.twitter.android", "color", "common_signin_btn_dark_text_disabled", modRes.fwd(R.color.common_signin_btn_dark_text_disabled));
        resparam.res.setReplacement("com.twitter.android", "color", "common_action_bar_splitter", modRes.fwd(R.color.common_action_bar_splitter));
        resparam.res.setReplacement("com.twitter.android", "color", "faint_gray", modRes.fwd(R.color.faint_gray));
        resparam.res.setReplacement("com.twitter.android", "color", "faint_blue", modRes.fwd(R.color.faint_blue));
        resparam.res.setReplacement("com.twitter.android", "color", "faded_gray", modRes.fwd(R.color.faded_gray));
        resparam.res.setReplacement("com.twitter.android", "color", "faded_blue", modRes.fwd(R.color.faded_blue));
        resparam.res.setReplacement("com.twitter.android", "color", "form_error", modRes.fwd(R.color.form_error));
		resparam.res.setReplacement("com.twitter.android", "color", "faint_transparent_white", modRes.fwd(R.color.faint_transparent_white));
		resparam.res.setReplacement("com.twitter.android", "color", "faint_transparent_blue", modRes.fwd(R.color.faint_transparent_blue));
		resparam.res.setReplacement("com.twitter.android", "color", "faint_transparent_black", modRes.fwd(R.color.faint_transparent_black));
		resparam.res.setReplacement("com.twitter.android", "color", "faint_night_mode_white", modRes.fwd(R.color.faint_night_mode_white));
	    resparam.res.setReplacement("com.twitter.android", "color", "dark_transparent_gray", modRes.fwd(R.color.dark_transparent_gray));
        resparam.res.setReplacement("com.twitter.android", "color", "dark_transparent_black", modRes.fwd(R.color.dark_transparent_black));
        resparam.res.setReplacement("com.twitter.android", "color", "clip_track_zoom_out", modRes.fwd(R.color.clip_track_zoom_out));
        resparam.res.setReplacement("com.twitter.android", "color", "camera_toolbar", modRes.fwd(R.color.camera_toolbar));
        resparam.res.setReplacement("com.twitter.android", "color", "camera_shutter_bar_highlighted", modRes.fwd(R.color.camera_shutter_bar_highlighted));
        resparam.res.setReplacement("com.twitter.android", "color", "camera_shutter_bar", modRes.fwd(R.color.camera_shutter_bar));
		resparam.res.setReplacement("com.twitter.android", "color", "camera_divider_line", modRes.fwd(R.color.camera_divider_line));
		resparam.res.setReplacement("com.twitter.android", "color", "button_text_disabled", modRes.fwd(R.color.button_text_disabled));
		resparam.res.setReplacement("com.twitter.android", "color", "btn_pressed_stroke", modRes.fwd(R.color.btn_pressed_stroke));
		resparam.res.setReplacement("com.twitter.android", "color", "btn_pressed_fill", modRes.fwd(R.color.btn_pressed_fill));
		resparam.res.setReplacement("com.twitter.android", "color", "btn_local_stroke", modRes.fwd(R.color.btn_local_stroke));
		resparam.res.setReplacement("com.twitter.android", "color", "btn_local_pressed_fill", modRes.fwd(R.color.btn_local_pressed_fill));
        resparam.res.setReplacement("com.twitter.android", "color", "btn_disabled_stroke", modRes.fwd(R.color.btn_disabled_stroke));
        resparam.res.setReplacement("com.twitter.android", "color", "btn_disabled_fill", modRes.fwd(R.color.btn_disabled_fill));
        resparam.res.setReplacement("com.twitter.android", "color", "btn_default_stroke", modRes.fwd(R.color.btn_default_stroke));
        resparam.res.setReplacement("com.twitter.android", "color", "btn_active_pressed_stroke", modRes.fwd(R.color.btn_active_pressed_stroke));
        resparam.res.setReplacement("com.twitter.android", "color", "btn_active_pressed_fill", modRes.fwd(R.color.btn_active_pressed_fill));
        resparam.res.setReplacement("com.twitter.android", "color", "btn_active_default_stroke", modRes.fwd(R.color.btn_active_default_stroke));
		resparam.res.setReplacement("com.twitter.android", "color", "btn_active_default_fill", modRes.fwd(R.color.btn_active_default_fill));
		resparam.res.setReplacement("com.twitter.android", "color", "border_contrast_light", modRes.fwd(R.color.border_contrast_light));
		resparam.res.setReplacement("com.twitter.android", "color", "border_contrast_dark", modRes.fwd(R.color.border_contrast_dark));
		resparam.res.setReplacement("com.twitter.android", "color", "alerts_fill_pressed", modRes.fwd(R.color.alerts_fill_pressed));
		resparam.res.setReplacement("com.twitter.android", "color", "alert_hashtag", modRes.fwd(R.color.alert_hashtag));
	    resparam.res.setReplacement("com.twitter.android", "color", "video_segment_selected", modRes.fwd(R.color.video_segment_selected));
		resparam.res.setReplacement("com.twitter.android", "color", "unread", modRes.fwd(R.color.unread));
		resparam.res.setReplacement("com.twitter.android", "color", "twitter_blue", modRes.fwd(R.color.twitter_blue));
		resparam.res.setReplacement("com.twitter.android", "color", "transparent_white", modRes.fwd(R.color.transparent_white));
        resparam.res.setReplacement("com.twitter.android", "color", "transparent_twitter_blue", modRes.fwd(R.color.transparent_twitter_blue));
        resparam.res.setReplacement("com.twitter.android", "color", "strong_white", modRes.fwd(R.color.strong_white));
        resparam.res.setReplacement("com.twitter.android", "color", "transparent_black", modRes.fwd(R.color.transparent_black));
        resparam.res.setReplacement("com.twitter.android", "color", "task_background_blue", modRes.fwd(R.color.task_background_blue));
        resparam.res.setReplacement("com.twitter.android", "color", "soft_white", modRes.fwd(R.color.soft_white));
        resparam.res.setReplacement("com.twitter.android", "color", "pressed", modRes.fwd(R.color.pressed));
		resparam.res.setReplacement("com.twitter.android", "color", "prefix", modRes.fwd(R.color.prefix));
		resparam.res.setReplacement("com.twitter.android", "color", "preference_category_text_color", modRes.fwd(R.color.preference_category_text_color));
		resparam.res.setReplacement("com.twitter.android", "color", "pip_inactive", modRes.fwd(R.color.pip_inactive));
		resparam.res.setReplacement("com.twitter.android", "color", "pip_active", modRes.fwd(R.color.pip_active));
		resparam.res.setReplacement("com.twitter.android", "color", "notification_icon_bg", modRes.fwd(R.color.notification_icon_bg));
		resparam.res.setReplacement("com.twitter.android", "color", "notification", modRes.fwd(R.color.notification));
        resparam.res.setReplacement("com.twitter.android", "color", "night_mode_pressed_fill", modRes.fwd(R.color.night_mode_pressed_fill));
        resparam.res.setReplacement("com.twitter.android", "color", "magic_recs_subtext", modRes.fwd(R.color.magic_recs_subtext));
        resparam.res.setReplacement("com.twitter.android", "color", "magic_recs_media_tint", modRes.fwd(R.color.magic_recs_media_tint));
        resparam.res.setReplacement("com.twitter.android", "color", "magic_recs_gradient_tint_start", modRes.fwd(R.color.magic_recs_gradient_tint_start));
        resparam.res.setReplacement("com.twitter.android", "color", "magic_recs_big_text", modRes.fwd(R.color.magic_recs_big_text));
        resparam.res.setReplacement("com.twitter.android", "color", "link_selected", modRes.fwd(R.color.link_selected));
		resparam.res.setReplacement("com.twitter.android", "color", "lighter_transparent_black", modRes.fwd(R.color.lighter_transparent_black));
		resparam.res.setReplacement("com.twitter.android", "color", "light_transparent_black", modRes.fwd(R.color.light_transparent_black));
		resparam.res.setReplacement("com.twitter.android", "color", "highlights_transparent_faded_gray", modRes.fwd(R.color.highlights_transparent_faded_gray));
		resparam.res.setReplacement("com.twitter.android", "color", "highlights_stroke", modRes.fwd(R.color.highlights_stroke));
		resparam.res.setReplacement("com.twitter.android", "color", "highlights_story_header_trend", modRes.fwd(R.color.highlights_story_header_trend));
        resparam.res.setReplacement("com.twitter.android", "color", "highlights_story_header_trend", modRes.fwd(R.color.highlights_story_header_trend));
        resparam.res.setReplacement("com.twitter.android", "color", "highlights_story_header_overlay_gradient_start", modRes.fwd(R.color.highlights_story_header_overlay_gradient_start));
        resparam.res.setReplacement("com.twitter.android", "color", "expandable_view_host_default_background_color", modRes.fwd(R.color.expandable_view_host_default_background_color));
        resparam.res.setReplacement("com.twitter.android", "color", "highlights_story_header_news", modRes.fwd(R.color.highlights_story_header_news));
        resparam.res.setReplacement("com.twitter.android", "color", "highlights_story_header_link", modRes.fwd(R.color.highlights_story_header_link));
        resparam.res.setReplacement("com.twitter.android", "color", "highlights_gray", modRes.fwd(R.color.highlights_gray));
		resparam.res.setReplacement("com.twitter.android", "color", "highlights_blue", modRes.fwd(R.color.highlights_blue));*/
        
    }
}