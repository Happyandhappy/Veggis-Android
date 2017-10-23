package com.developer.android.quickveggis.ui.utils;

import android.net.Uri;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.util.SparseArray;
import android.webkit.MimeTypeMap;
import com.j256.ormlite.stmt.query.SimpleComparison;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import javax.net.ssl.HttpsURLConnection;
import xyz.hanks.library.BuildConfig;

public final class YouTubeExtractor {
    public static final int YOUTUBE_VIDEO_QUALITY_HD_1080 = 37;
    public static final int YOUTUBE_VIDEO_QUALITY_HD_720 = 22;
    public static final int YOUTUBE_VIDEO_QUALITY_MEDIUM_360 = 18;
    public static final int YOUTUBE_VIDEO_QUALITY_SMALL_240 = 36;
    private boolean mCancelled;
    private HttpsURLConnection mConnection;
    private final List<String> mElFields;
    private List<Integer> mPreferredVideoQualities;
    private final String mVideoIdentifier;

    /* renamed from: com.quickveggies.quickveggies.ui.utils.YouTubeExtractor.1 */
    class C03401 implements Runnable {
        final /* synthetic */ String val$language;
        final /* synthetic */ String val$link;
        final /* synthetic */ YouTubeExtractorListener val$listener;
        final /* synthetic */ Handler val$listenerHandler;
        final /* synthetic */ HandlerThread val$youtubeExtractorThread;

        /* renamed from: com.quickveggies.quickveggies.ui.utils.YouTubeExtractor.1.1 */
        class C03381 implements Runnable {
            final /* synthetic */ YouTubeExtractorResult val$result;

            C03381(YouTubeExtractorResult youTubeExtractorResult) {
                this.val$result = youTubeExtractorResult;
            }

            public void run() {
                if (!YouTubeExtractor.this.mCancelled && C03401.this.val$listener != null) {
                    C03401.this.val$listener.onSuccess(this.val$result);
                }
            }
        }

        /* renamed from: com.quickveggies.quickveggies.ui.utils.YouTubeExtractor.1.2 */
        class C03392 implements Runnable {
            final /* synthetic */ Exception val$e;

            C03392(Exception exception) {
                this.val$e = exception;
            }

            public void run() {
                if (!YouTubeExtractor.this.mCancelled && C03401.this.val$listener != null) {
                    C03401.this.val$listener.onFailure(new Error(this.val$e));
                }
            }
        }

        public void run() {
            /* JADX: method processing error */
/*
            Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find block by offset: 0x0066 in list []
	at jadx.core.utils.BlockUtils.getBlockByOffset(BlockUtils.java:42)
	at jadx.core.dex.instructions.IfNode.initBlocks(IfNode.java:58)
	at jadx.core.dex.visitors.blocksmaker.BlockFinish.initBlocksInIfNodes(BlockFinish.java:48)
	at jadx.core.dex.visitors.blocksmaker.BlockFinish.visit(BlockFinish.java:33)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:281)
	at jadx.api.JavaClass.decompile(JavaClass.java:59)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:161)
*/
            /*
            r8 = this;
            r6 = com.quickveggies.quickveggies.ui.utils.YouTubeExtractor.this;	 Catch:{ Exception -> 0x004a, all -> 0x00a2 }
            r5 = new java.net.URL;	 Catch:{ Exception -> 0x004a, all -> 0x00a2 }
            r7 = r8.val$link;	 Catch:{ Exception -> 0x004a, all -> 0x00a2 }
            r5.<init>(r7);	 Catch:{ Exception -> 0x004a, all -> 0x00a2 }
            r5 = r5.openConnection();	 Catch:{ Exception -> 0x004a, all -> 0x00a2 }
            r5 = (javax.net.ssl.HttpsURLConnection) r5;	 Catch:{ Exception -> 0x004a, all -> 0x00a2 }
            r6.mConnection = r5;	 Catch:{ Exception -> 0x004a, all -> 0x00a2 }
            r5 = com.quickveggies.quickveggies.ui.utils.YouTubeExtractor.this;	 Catch:{ Exception -> 0x004a, all -> 0x00a2 }
            r5 = r5.mConnection;	 Catch:{ Exception -> 0x004a, all -> 0x00a2 }
            r6 = "Accept-Language";	 Catch:{ Exception -> 0x004a, all -> 0x00a2 }
            r7 = r8.val$language;	 Catch:{ Exception -> 0x004a, all -> 0x00a2 }
            r5.setRequestProperty(r6, r7);	 Catch:{ Exception -> 0x004a, all -> 0x00a2 }
            r3 = new java.io.BufferedReader;	 Catch:{ Exception -> 0x004a, all -> 0x00a2 }
            r5 = new java.io.InputStreamReader;	 Catch:{ Exception -> 0x004a, all -> 0x00a2 }
            r6 = com.quickveggies.quickveggies.ui.utils.YouTubeExtractor.this;	 Catch:{ Exception -> 0x004a, all -> 0x00a2 }
            r6 = r6.mConnection;	 Catch:{ Exception -> 0x004a, all -> 0x00a2 }
            r6 = r6.getInputStream();	 Catch:{ Exception -> 0x004a, all -> 0x00a2 }
            r5.<init>(r6);	 Catch:{ Exception -> 0x004a, all -> 0x00a2 }
            r3.<init>(r5);	 Catch:{ Exception -> 0x004a, all -> 0x00a2 }
            r0 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x004a, all -> 0x00a2 }
            r0.<init>();	 Catch:{ Exception -> 0x004a, all -> 0x00a2 }
        L_0x0038:
            r2 = r3.readLine();	 Catch:{ Exception -> 0x004a, all -> 0x00a2 }
            if (r2 == 0) goto L_0x006c;	 Catch:{ Exception -> 0x004a, all -> 0x00a2 }
        L_0x003e:
            r5 = com.quickveggies.quickveggies.ui.utils.YouTubeExtractor.this;	 Catch:{ Exception -> 0x004a, all -> 0x00a2 }
            r5 = r5.mCancelled;	 Catch:{ Exception -> 0x004a, all -> 0x00a2 }
            if (r5 != 0) goto L_0x006c;	 Catch:{ Exception -> 0x004a, all -> 0x00a2 }
        L_0x0046:
            r0.append(r2);	 Catch:{ Exception -> 0x004a, all -> 0x00a2 }
            goto L_0x0038;
        L_0x004a:
            r1 = move-exception;
            r5 = r8.val$listenerHandler;	 Catch:{ Exception -> 0x004a, all -> 0x00a2 }
            r6 = new com.quickveggies.quickveggies.ui.utils.YouTubeExtractor$1$2;	 Catch:{ Exception -> 0x004a, all -> 0x00a2 }
            r6.<init>(r1);	 Catch:{ Exception -> 0x004a, all -> 0x00a2 }
            r5.post(r6);	 Catch:{ Exception -> 0x004a, all -> 0x00a2 }
            r5 = com.quickveggies.quickveggies.ui.utils.YouTubeExtractor.this;
            r5 = r5.mConnection;
            if (r5 == 0) goto L_0x0066;
        L_0x005d:
            r5 = com.quickveggies.quickveggies.ui.utils.YouTubeExtractor.this;
            r5 = r5.mConnection;
            r5.disconnect();
        L_0x0066:
            r5 = r8.val$youtubeExtractorThread;
            r5.quit();
        L_0x006b:
            return;
        L_0x006c:
            r3.close();	 Catch:{ Exception -> 0x004a, all -> 0x00a2 }
            r5 = com.quickveggies.quickveggies.ui.utils.YouTubeExtractor.this;	 Catch:{ Exception -> 0x004a, all -> 0x00a2 }
            r5 = r5.mCancelled;	 Catch:{ Exception -> 0x004a, all -> 0x00a2 }
            if (r5 != 0) goto L_0x008b;	 Catch:{ Exception -> 0x004a, all -> 0x00a2 }
        L_0x0077:
            r5 = com.quickveggies.quickveggies.ui.utils.YouTubeExtractor.this;	 Catch:{ Exception -> 0x004a, all -> 0x00a2 }
            r6 = r0.toString();	 Catch:{ Exception -> 0x004a, all -> 0x00a2 }
            r4 = r5.getYouTubeResult(r6);	 Catch:{ Exception -> 0x004a, all -> 0x00a2 }
            r5 = r8.val$listenerHandler;	 Catch:{ Exception -> 0x004a, all -> 0x00a2 }
            r6 = new com.quickveggies.quickveggies.ui.utils.YouTubeExtractor$1$1;	 Catch:{ Exception -> 0x004a, all -> 0x00a2 }
            r6.<init>(r4);	 Catch:{ Exception -> 0x004a, all -> 0x00a2 }
            r5.post(r6);	 Catch:{ Exception -> 0x004a, all -> 0x00a2 }
        L_0x008b:
            r5 = com.quickveggies.quickveggies.ui.utils.YouTubeExtractor.this;
            r5 = r5.mConnection;
            if (r5 == 0) goto L_0x009c;
        L_0x0093:
            r5 = com.quickveggies.quickveggies.ui.utils.YouTubeExtractor.this;
            r5 = r5.mConnection;
            r5.disconnect();
        L_0x009c:
            r5 = r8.val$youtubeExtractorThread;
            r5.quit();
            goto L_0x006b;
        L_0x00a2:
            r5 = move-exception;
            r6 = com.quickveggies.quickveggies.ui.utils.YouTubeExtractor.this;
            r6 = r6.mConnection;
            if (r6 == 0) goto L_0x00b4;
        L_0x00ab:
            r6 = com.quickveggies.quickveggies.ui.utils.YouTubeExtractor.this;
            r6 = r6.mConnection;
            r6.disconnect();
        L_0x00b4:
            r6 = r8.val$youtubeExtractorThread;
            r6.quit();
            throw r5;
            */
//            throw new UnsupportedOperationException("Method not decompiled: com.quickveggies.quickveggies.ui.utils.YouTubeExtractor.1.run():void");
        }

        C03401(String str, String str2, Handler handler, YouTubeExtractorListener youTubeExtractorListener, HandlerThread handlerThread) {
            this.val$link = str;
            this.val$language = str2;
            this.val$listenerHandler = handler;
            this.val$listener = youTubeExtractorListener;
            this.val$youtubeExtractorThread = handlerThread;
        }
    }

    public final class YouTubeExtractorException extends Exception {
        public YouTubeExtractorException(String detailMessage) {
            super(detailMessage);
        }
    }

    public interface YouTubeExtractorListener {
        void onFailure(Error error);

        void onSuccess(YouTubeExtractorResult youTubeExtractorResult);
    }

    public static final class YouTubeExtractorResult {
        private final Uri mDefaultThumbUri;
        private final Uri mHighThumbUri;
        private final Uri mMediumThumbUri;
        private final Uri mStandardThumbUri;
        private final Uri mVideoUri;

        private YouTubeExtractorResult(Uri videoUri, Uri mediumThumbUri, Uri highThumbUri, Uri defaultThumbUri, Uri standardThumbUri) {
            this.mVideoUri = videoUri;
            this.mMediumThumbUri = mediumThumbUri;
            this.mHighThumbUri = highThumbUri;
            this.mDefaultThumbUri = defaultThumbUri;
            this.mStandardThumbUri = standardThumbUri;
        }

        public Uri getVideoUri() {
            return this.mVideoUri;
        }

        public Uri getMediumThumbUri() {
            return this.mMediumThumbUri;
        }

        public Uri getHighThumbUri() {
            return this.mHighThumbUri;
        }

        public Uri getDefaultThumbUri() {
            return this.mDefaultThumbUri;
        }

        public Uri getStandardThumbUri() {
            return this.mStandardThumbUri;
        }
    }

    public YouTubeExtractor(String videoIdentifier) {
        this.mVideoIdentifier = videoIdentifier;
        this.mElFields = new ArrayList(Arrays.asList(new String[]{"embedded", "detailpage", "vevo", BuildConfig.FLAVOR}));
        this.mPreferredVideoQualities = Arrays.asList(new Integer[]{Integer.valueOf(YOUTUBE_VIDEO_QUALITY_MEDIUM_360), Integer.valueOf(YOUTUBE_VIDEO_QUALITY_SMALL_240), Integer.valueOf(YOUTUBE_VIDEO_QUALITY_HD_720), Integer.valueOf(YOUTUBE_VIDEO_QUALITY_HD_1080)});
    }

    public List<Integer> getPreferredVideoQualities() {
        return this.mPreferredVideoQualities;
    }

    public void setPreferredVideoQualities(List<Integer> preferredVideoQualities) {
        this.mPreferredVideoQualities = preferredVideoQualities;
    }

    public void startExtracting(YouTubeExtractorListener listener) {
        String elField = (String) this.mElFields.get(0);
        this.mElFields.remove(0);
        if (elField.length() > 0) {
            elField = "&el=" + elField;
        }
        String language = Locale.getDefault().getLanguage();
        String link = String.format("https://www.youtube.com/get_video_info?video_id=%s%s&ps=default&eurl=&gl=US&hl=%s", new Object[]{this.mVideoIdentifier, elField, Locale.getDefault().getLanguage()});
        HandlerThread youtubeExtractorThread = new HandlerThread("YouTubeExtractorThread", 10);
        youtubeExtractorThread.start();
        new Handler(youtubeExtractorThread.getLooper()).post(new C03401(link, language, new Handler(Looper.getMainLooper()), listener, youtubeExtractorThread));
    }

    public void cancelExtracting() {
        this.mCancelled = true;
    }

    private static HashMap<String, String> getQueryMap(String queryString, String charsetName) throws UnsupportedEncodingException {
        HashMap<String, String> map = new HashMap();
        for (String field : queryString.split("&")) {
            String[] pair = field.split(SimpleComparison.EQUAL_TO_OPERATION);
            if (pair.length == 2) {
                map.put(pair[0], URLDecoder.decode(pair[1], charsetName).replace('+', ' '));
            }
        }
        return map;
    }

    private YouTubeExtractorResult getYouTubeResult(String html) throws UnsupportedEncodingException, YouTubeExtractorException {
        HashMap<String, String> video = getQueryMap(html, "UTF-8");
        Uri videoUri = null;
        if (video.containsKey("url_encoded_fmt_stream_map")) {
            Uri mediumThumbUri;
            Uri highThumbUri;
            Uri defaultThumbUri;
            Uri standardThumbUri;
            List<String> streamQueries = new ArrayList(Arrays.asList(((String) video.get("url_encoded_fmt_stream_map")).split(",")));
            streamQueries.addAll(Arrays.asList(((String) video.get("adaptive_fmts")).split(",")));
            SparseArray<String> streamLinks = new SparseArray();
            for (String queryMap : streamQueries) {
                String queryMap2 = queryMap;
                HashMap<String, String> stream = getQueryMap(queryMap2, "UTF-8");
                String type = ((String) stream.get("type")).split(";")[0];
                String urlString = (String) stream.get("url");
                if (urlString != null && MimeTypeMap.getSingleton().hasMimeType(type)) {
                    String signature = (String) stream.get("sig");
                    if (signature != null) {
                        urlString = urlString + "&signature=" + signature;
                    }
                    if (getQueryMap(urlString, "UTF-8").containsKey("signature")) {
                        streamLinks.put(Integer.parseInt((String) stream.get("itag")), urlString);
                    }
                }
            }
            for (Integer videoQuality : this.mPreferredVideoQualities) {
                if (streamLinks.get(videoQuality.intValue(), null) != null) {
                    videoUri = Uri.parse((String) streamLinks.get(videoQuality.intValue()));
                    break;
                }
            }
            if (video.containsKey("iurlmq")) {
                mediumThumbUri = Uri.parse((String) video.get("iurlmq"));
            } else {
                mediumThumbUri = null;
            }
            if (video.containsKey("iurlhq")) {
                highThumbUri = Uri.parse((String) video.get("iurlhq"));
            } else {
                highThumbUri = null;
            }
            if (video.containsKey("iurl")) {
                defaultThumbUri = Uri.parse((String) video.get("iurl"));
            } else {
                defaultThumbUri = null;
            }
            if (video.containsKey("iurlsd")) {
                standardThumbUri = Uri.parse((String) video.get("iurlsd"));
            } else {
                standardThumbUri = null;
            }
            return new YouTubeExtractorResult(mediumThumbUri, highThumbUri, defaultThumbUri, standardThumbUri, null);
        }
//        StringBuilder append = new StringBuilder().append("Status: ");
//        String queryMap2 = "\nReason: ";
//        StringBuilder append2 = append.append((String) video.get(NotificationCompatApi21.CATEGORY_STATUS)).append(r21);
//        append = append2;
//        String queryMap2 = "\nError code: ";
//        append2 = append.append((String) video.get("reason")).append(r21);
//        throw new YouTubeExtractorException(append2.append((String) video.get("errorcode")).toString());
        return null;
    }
}
