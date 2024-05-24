package dao;

import dto.ReviewImageDTO;

public interface IReviewImageDAO {

	int insert(ReviewImageDTO reviewImageDTO);
	
	ReviewImageDTO select(int reviewId);
}
