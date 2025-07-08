import { useEffect, useState } from "react";
import { motion, AnimatePresence } from "framer-motion";
import SidebarProfile from "../components/SidebarProfile";
import ProgressBar from "../components/ProgressBar";
import Navbar from "../components/Navbar";
import UserActivityHeatmap from "../components/UserActivityHeatmap"; // optional

const DashboardPage = () => {
  const username = localStorage.getItem("username") || "User";

  // Rotating quotes
  const quotes = [
    "Consistency always wins over talent.",
    "Code is like humor. When you have to explain it, it’s bad.",
    "Discipline is the bridge between goals and accomplishment.",
    "Strive for progress, not perfection.",
    "Your future is created by what you do today, not tomorrow.",
    "Success is the sum of small efforts repeated day in and day out.",
    "The best way to predict the future is to create it.",
    "Every expert was once a beginner.",
    "Dream big, start small, act now.",
    "Push yourself, because no one else is going to do it for you.",
    "Great developers write less code and do more with it.",
    "Learn by building — not just reading.",
    "Your code should tell a story anyone can follow.",
    "Motivation gets you started, discipline keeps you going.",
    "Effort beats talent when talent doesn’t work hard.",
  ];

  const [quoteIndex, setQuoteIndex] = useState(0);

  useEffect(() => {
    const interval = setInterval(() => {
      setQuoteIndex((prev) => (prev + 1) % quotes.length);
    }, 6000);
    return () => clearInterval(interval);
  }, []);

  return (
    <>
      <Navbar />
      <div className="min-h-screen py-3 px-14 sm:px-6 lg:px-12">
        <div className="sm:p-8">
          <div className="grid grid-cols-1 lg:grid-cols-3 gap-6 items-start">
            {/* Sidebar */}
            <div className="lg:col-span-1 w-full">
              <div className="bg-[#1A1A1A] rounded-xl p-4 sm:p-6 shadow-inner border border-orange-500 flex flex-col justify-between h-full gap-4">
                <SidebarProfile />
              </div>
            </div>

            {/* Main Dashboard */}
            <div className="lg:col-span-2 w-full flex flex-col gap-6">
              {/* Progress Section */}
              <div className="bg-[#1A1A1A] rounded-xl p-4 sm:p-6 shadow-inner border border-orange-500 ">
                {/* Heading + Quote inline */}
                <div className="flex flex-col sm:flex-row justify-between items-start sm:items-center mb-4 gap-2">
                  <h2 className="text-xl font-semibold text-orange-400">
                    Learning Progress
                  </h2>
                  <div className="text-sm italic text-gray-400 max-w-md text-right">
                    <AnimatePresence mode="wait">
                      <motion.p
                        key={quoteIndex}
                        initial={{ opacity: 0, y: 6 }}
                        animate={{ opacity: 1, y: 0 }}
                        exit={{ opacity: 0, y: -6 }}
                        transition={{ duration: 0.4 }}
                      >
                        “{quotes[quoteIndex]}”
                      </motion.p>
                    </AnimatePresence>
                  </div>
                </div>

                <ProgressBar />
              </div>
            </div>
          </div>
        </div>
      </div>
    </>
  );
};

export default DashboardPage;
