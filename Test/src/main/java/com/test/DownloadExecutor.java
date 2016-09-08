package com.test;

import java.util.List;

public interface DownloadExecutor<E> {

	List<E> doDownload(int page, int pageSize);
}
