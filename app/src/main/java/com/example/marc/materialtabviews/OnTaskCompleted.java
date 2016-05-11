package com.example.marc.materialtabviews;

import java.util.List;

public interface OnTaskCompleted {
    void onTaskCompleted(Boolean err, List<Object> data);
}
