package com.railway.dao;

import com.railway.model.Train;
import java.util.List;

public interface TrainDAO {
    boolean addTrain(Train train);
    boolean deleteTrain(int trainId);
    Train getTrainById(int trainId);
    List<Train> getAllTrains();
}
