package com.theforum.shell;

/**
 * @author Uliana and David
 */
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import com.theforum.dao.PostManager;
import com.theforum.dao.PostManagerImpl;
import com.theforum.entities.Posts;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

//Delete old posts class as singelton for sheldure task
public class DeletePosts implements Job {
	private static volatile DeletePosts instance;
	PostManager postManager = new PostManagerImpl();

	int postLiveTimeDays = 30;

	public static DeletePosts getInstance() {
		DeletePosts deletePosts = instance;
		if (deletePosts == null) {
			synchronized (DeletePosts.class) {
				deletePosts = instance;
				if (deletePosts == null) {
					instance = deletePosts = new DeletePosts();
				}
			}
		}
		return deletePosts;
	}

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		// test only task
		System.out.println("My First Quartz Scheduler");
		LocalDateTime now = LocalDateTime.now();
		String isoFormat = DateTimeFormatter.ISO_INSTANT.format(now.toInstant(ZoneOffset.UTC));
		System.out.println(isoFormat);

		// delete old posts task

		List<Posts> posts = postManager.loadAllPosts();

		Calendar c = new GregorianCalendar();
		c.add(Calendar.DATE, -postLiveTimeDays);
		Date d = c.getTime();

		for (Posts item : posts) {
			if (item != null && item.getPostDate().before(d)) {
				postManager.deletePost(item);
			}
		}
		int tmp = 1;
	}

}
